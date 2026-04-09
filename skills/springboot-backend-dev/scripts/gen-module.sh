#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 2 ]]; then
  echo "Usage: $0 <base-dir> <module-name>"
  echo "Example: $0 src/main/java/com/example user"
  exit 1
fi

base_dir="$1"
module="$2"
module_dir="$base_dir/$module"

if [[ -d "$module_dir" ]]; then
  echo "Module already exists: $module_dir"
  exit 1
fi

mkdir -p "$module_dir/controller" "$module_dir/service" "$module_dir/repository" "$module_dir/dto"

echo "package ${module};" > "$module_dir/package-info.java"
echo "Created module skeleton: $module_dir"
