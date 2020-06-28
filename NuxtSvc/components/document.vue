<template>
  <div>
    <div class="p-1 h-100">
      <div class="h-100 border rounded">
        <div class="d-flex p-1 bg-light">
          <div class="flex-fill">
            <h6 class="p-2 m-0">
              <strong>文書</strong>
            </h6>
          </div>
          <b-button size="sm" @click="click()">
            登録
          </b-button>
        </div>
        <div class="p-1">
          <div v-for="(doc, index) in documents" :key="doc.id">
            <list-doc-item :document="doc" :index="index" />
          </div>
        </div>
      </div>
    </div>
    <post-doc-modal ref="PostDocModal" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import postDocModal from './post-doc-modal';
import ListDocItem from './list-doc-item.vue';

export default {
  components: {
    'post-doc-modal': postDocModal,
    'list-doc-item': ListDocItem
  },
  computed: {
    ...mapGetters('repository', [
      'documents'
      // ...
    ])
  },
  methods: {
    click() {
      this.$refs.PostDocModal.start();
    }
  }
};
</script>
