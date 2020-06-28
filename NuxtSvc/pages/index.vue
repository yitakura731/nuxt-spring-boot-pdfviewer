<template>
  <b-container class="h-100">
    <b-row class="h-100">
      <b-col cols="4" class="px-0">
        <div class="d-flex flex-column h-100">
          <document class="flex-fill" />
        </div>
      </b-col>
      <b-col cols="8" class="px-0">
        <viewer class="h-100" />
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import Document from '@@/components/document.vue';
import Viewer from '@@/components/viewer.vue';

export default {
  layout: 'main',
  middleware: ['initialize'],
  components: {
    document: Document,
    viewer: Viewer
  }
};
</script>
