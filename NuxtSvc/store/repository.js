import pdfjsLib from 'pdfjs-dist';

export const state = () => ({
  documents: [],
  selectedDocument: null
});

export const mutations = {
  documents(state, input) {
    state.documents = input;
  },
  selectedDocument(state, input) {
    state.selectedDocument = input;
  }
};

export const getters = {
  documents: state => {
    return state.documents;
  },
  selectedDocument: state => {
    return state.selectedDocument;
  }
};

export const actions = {
  async postDocument({ dispatch }, args) {
    await this.$axios.$post(`/api/rep/documents`, args.formData);
    await dispatch('fetchDocument');
  },

  async fetchDocument({ commit, getters }) {
    const url = `/api/rep/documents`;
    const response = await this.$axios.$get(url);
    if (response.length > 0) {
      commit('documents', response);
      commit('selectedDocument', response[0]);
    } else {
      commit('documents', null);
      commit('selectedDocument', null);
    }
  },

  fetchFile({ dispatch }, args) {
    const loadingTask = pdfjsLib.getDocument({
      url: `/api/rep/documents/${args.docId}/files`
    });
    return loadingTask.promise;
  }
};
