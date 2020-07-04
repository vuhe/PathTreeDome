<template>
    <div>
        <el-input
                placeholder="输入关键字进行过滤"
                v-model="filterText">
        </el-input>
        <el-tree
                :props="props"
                :load="loadNode"
                node-key="path"
                :filter-node-method="filterNode"
                ref="tree"
                lazy>
        </el-tree>
    </div>

</template>

<script>
export default {
  data () {
    return {
      filterText: '',
      props: {
        label: 'name',
        children: 'children',
        isLeaf: 'leaf'
      }
    }
  },
  watch: {
    filterText (val) {
      this.$refs.tree.filter(val)
    }
  },
  methods: {
    filterNode (value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    async loadNode (node, resolve) {
      if (node.level === 0) {
        return resolve([{ name: '根目录', path: '/' }])
      }

      let path = require('path')
      let fs = require('fs')
      let children = []
      fs.readdir(node.key, (err, files) => {
        if (err) {
          console.error(err)
        }
        if (files && files.length) {
          files.forEach(function (filename) {
            let filepath = path.join(node.key, filename)
            let isFile = fs.lstatSync(filepath).isFile()
            children.push({
              name: filename,
              path: filepath,
              leaf: isFile
            })
          })
        }
      })

      setTimeout(() => {
        resolve(children)
      }, 10)
    }
  }
}
</script>

<style scoped>
.el-tree-node__label {
    font-size: 20px;
}
</style>
