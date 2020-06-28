<template>
  <div class="d-flex flex-column h-100 border rounded">
    <pdf-viewer-operation
      ref="operationArea"
      :num-pages="numPages"
      class="border-bottom"
      @render="render"
    />
    <div ref="contentsArea" class="flex-fill">
      <div
        ref="canvasParent"
        class="canvas-parent overflow-auto"
        :style="{ height: height + 'px' }"
      >
        <canvas ref="contentsCanvas" />
      </div>
      <!-- <div id="textLayerDiv" ref="textLayerArea" /> -->
      <div class="fade-area">
        <transition name="fade" class="fade-area">
          <loading-spinner v-show="loading" />
        </transition>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import { TextLayerBuilder } from 'pdfjs-dist/web/pdf_viewer';
import PdfViewerOperation from './pdf-viewer-operation.vue';
import LoadingSpinner from './loading-spinner';

export default {
  components: {
    'loading-spinner': LoadingSpinner,
    'pdf-viewer-operation': PdfViewerOperation
  },
  props: {
    content: {
      required: true,
      validator: content => typeof (content === Object || content === null)
    }
  },
  data() {
    return {
      numPages: 1,
      loading: false,
      height: 0
    };
  },
  watch: {
    content(newVal, oldVal) {
      if (newVal) {
        this.$refs.operationArea.resetPage();
        this.render(1);
      }
    }
  },
  mounted() {
    this.matchHeight();
  },
  methods: {
    ...mapActions('repository', ['fetchFile']),
    render(pageIndex) {
      const parent = this.$refs.canvasParent;
      const canvas = this.$refs.contentsCanvas;
      const context = canvas.getContext('2d');
      this.loading = true;
      let page = null;
      let viewport = null;
      this.fetchFile({
        docId: this.content.id
      })
        .then(pdf => {
          this.numPages = pdf.numPages;
          return pdf.getPage(pageIndex);
        })
        .then(p => {
          page = p;
          const pWidth = parent.offsetWidth;
          const orgViewport = page.getViewport(1);
          const x1 = pWidth / orgViewport.width;
          viewport = page.getViewport(x1);
          canvas.width = viewport.width;
          canvas.height = viewport.height;
          return page.render({
            canvasContext: context,
            viewport: viewport
          });
        })
        .then(() => {
          return page.getTextContent();
        })
        .then(textContent => {
          // eslint-disable-next-line no-console
          const textLayerDiv = this.$refs.textLayerArea;
          const textLayer = new TextLayerBuilder({
            textLayerDiv: textLayerDiv,
            pageIndex: page.pageIndex,
            viewport: viewport
          });
          textLayer.setTextContent(textContent);
          return textLayer.render();
        })
        .then(() => {
          this.loading = false;
        })
        // eslint-disable-next-line handle-callback-err
        .catch(error => {
          this.loading = false;
        });
    },
    matchHeight() {
      const contentsArea = this.$refs.contentsArea;
      this.height = contentsArea.clientHeight;
    }
  }
};
</script>

<style scoped>
.canvas-parent {
  background-color: lightgray;
}
.fade-area {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateY(-50%) translateX(-50%);
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 2s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
