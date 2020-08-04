<template>
  <div id="test">
    <h1>테스트</h1>
    <editor />
  </div>
</template>

<script>
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";

import { Editor } from "@toast-ui/vue-editor";

export default {
  name: "test",
  components: {
    editor: Editor
  }
};
</script>

<style>
</style>