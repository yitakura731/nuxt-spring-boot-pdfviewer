<template>
  <b-modal
    ref="errorModal"
    size="sm"
    hide-header
    hide-footer
    body-bg-variant="warning"
    no-close-on-backdrop
    centered
  >
    <div class="w-100 text-center d-flex">
      <div class="flex-fill">
        <div>{{ errorMessage }}</div>
      </div>
      <div>
        <b-button variant="light" @click="hideModal()">
          <font-awesome-icon :icon="['fas', 'times']" />
        </b-button>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  data() {
    return {
      errorMessage: null
    };
  },
  methods: {
    showModal(err) {
      this.errorMessage = this.createErrorMessage(err);
      this.$refs.errorModal.show();
    },
    hideModal() {
      this.$refs.errorModal.hide();
    },
    createErrorMessage(error) {
      let retVal = null;
      if (
        error.response != null &&
        error.response.data != null &&
        error.response.data.error != null
      ) {
        retVal = error.response.data.error;
      } else {
        retVal = error;
      }
      return retVal;
    }
  }
};
</script>
