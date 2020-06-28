<template>
  <div :class="docItem" class="p-1" @click="click()">
    <div class="m-1 mr-2">
      {{ document.name }}
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  props: {
    document: {
      type: Object,
      required: true,
      validator(obj) {
        return typeof obj.id === 'number' && typeof obj.name === 'string';
      }
    }
  },
  computed: {
    ...mapGetters('repository', ['selectedDocument']),
    docItem() {
      if (
        this.selectedDocument &&
        this.selectedDocument.id === this.document.id
      ) {
        return 'selected-doc-item';
      } else {
        return 'doc-item';
      }
    }
  },
  methods: {
    click() {
      this.$store.commit('repository/selectedDocument', this.document);
    }
  }
};
</script>

<style scoped>
.doc-item {
  background-color: white;
  font-size: 1rem;
}
.doc-item:hover {
  background-color: blanchedalmond;
}
.selected-doc-item {
  background-color: #ffcc66;
  font-size: 1rem;
}
</style>
