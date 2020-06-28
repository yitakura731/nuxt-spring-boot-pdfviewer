<template>
  <b-form inline class="bg-light p-1">
    <b-button type="button" size="sm" class="ml-1" @click="setPage(-1)">
      <font-awesome-icon icon="caret-left" />
    </b-button>
    <b-button type="button" size="sm" class="ml-1" @click="setPage(1)">
      <font-awesome-icon icon="caret-right" />
    </b-button>
    <div class="contents-number ml-1">
      {{ currPage }}
    </div>
    <div class="ml-1 contents-label">
      /
    </div>
    <div class="contents-number ml-1">
      {{ numPages }}
    </div>
  </b-form>
</template>

<script>
export default {
  props: {
    numPages: {
      required: true,
      validator: numPages => typeof (numPages === Number)
    }
  },
  data() {
    return {
      currPage: 1
    };
  },
  methods: {
    resetPage() {
      this.currPage = 1;
    },
    setPage(input) {
      if (this.currPage + input > 0 && this.currPage + input <= this.numPages) {
        this.currPage += input;
        this.$emit('render', this.currPage);
      }
    }
  }
};
</script>

<style scoped>
.contents-number {
  font-size: 17px;
  text-align: center;
  border: thin solid gray;
  width: 40px;
  border-radius: 5px;
  background-color: white;
}
.contents-label {
  font-size: 20px;
}
</style>
