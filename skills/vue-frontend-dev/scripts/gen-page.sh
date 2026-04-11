#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 2 ]]; then
  echo "Usage: $0 <output-dir> <page-name>"
  echo "Example: $0 src/pages UserList"
  exit 1
fi

output_dir="$1"
page_name="$2"

mkdir -p "$output_dir"
file_path="$output_dir/${page_name}.vue"

if [[ -f "$file_path" ]]; then
  echo "File already exists: $file_path"
  exit 1
fi

cat > "$file_path" <<VUE
<template>
  <section class="${page_name}-page">
    <h1>${page_name}</h1>
  </section>
</template>

<script setup lang="ts">
</script>

<style scoped>
.${page_name}-page {
  padding: 16px;
}
</style>
VUE

echo "Created: $file_path"
