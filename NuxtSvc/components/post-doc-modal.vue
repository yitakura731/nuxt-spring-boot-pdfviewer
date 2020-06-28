<template>
  <div>
    <b-modal
      ref="postDocModal"
      hide-footer
      hide-header-close
      title="登録"
      header-bg-variant="light"
      header-class="py-2 justify-content-center"
    >
      <b-form-group>
        <label class="mb-1">
          ファイル
          <b-badge pill variant="info">
            必須
          </b-badge>
        </label>
        <b-form-file placeholder="Choose a file..." @change="onChangeFile" />
      </b-form-group>
      <hr />
      <b-button class="w-100" variant="outline-secondary" @click="post">
        登録
      </b-button>
    </b-modal>
    <error-modal ref="errorModal" />
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import ErrorModal from './error-modal';

export default {
  components: {
    'error-modal': ErrorModal
  },
  data() {
    return {
      docFile: null
    };
  },
  methods: {
    ...mapActions('repository', ['postDocument']),
    start() {
      this.docFile = null;
      this.$refs.postDocModal.show();
    },
    onChangeFile(e) {
      e.preventDefault();
      this.docFile = e.target.files[0];
    },
    async post() {
      const fData = new FormData();
      fData.append('docFile', this.docFile);
      try {
        await this.postDocument({ formData: fData });
        await this.$refs.postDocModal.hide();
      } catch (e) {
        this.$refs.errorModal.showModal(e);
        this.$refs.postDocModal.hide();
      }
    }
  }
};
</script>

<style scoped></style>
