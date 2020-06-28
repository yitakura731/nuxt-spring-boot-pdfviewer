<template>
  <div class="p-1">
    <pdf-viewer :content="selectedDocument" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import PDFViewer from './pdf-viewer';

export default {
  components: {
    'pdf-viewer': PDFViewer
  },
  computed: {
    ...mapGetters('repository', ['selectedDocument'])
  }
};
</script>
