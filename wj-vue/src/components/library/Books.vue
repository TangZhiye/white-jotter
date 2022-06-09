<template>
  <div>
    <el-row style="height: 100%;">
<!--  图书搜索框    -->
      <search-bar @onSearch="searchResult()" ref="search"></search-bar>
      <el-tooltip effect="dark" placement="right"
                  v-for="item in books.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                  :key="item.id">
        <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.title}}</p>
        <p slot="content" style="font-size: 13px;margin-bottom: 6px">
          <span>{{item.author}}</span> /
          <span>{{item.date}}</span> /
          <span>{{item.press}}</span>
        </p>
        <p slot="content" style="width: 300px" class="abstract">{{item.abs}}</p>
        <el-card style="width: 135px;margin-bottom: 20px;height: 100%;float: left;margin-right: 15px" class="book"
                 bodyStyle="padding:10px" shadow="hover">
<!--      点击图片删除    -->
          <div class="cover" @click="editBook(item)">
            <img :src="item.cover" alt="封面">
          </div>
          <div class="info">
            <div class="title">
              <a href="">{{item.title}}</a>
            </div>
            <!--        删除图标    -->
            <div style="display:block;float:right;">
              <i class="el-icon-delete" @click="deleteBook(item.id)" style="cursor: pointer"></i>
            </div>
          </div>
          <div class="author">
            {{item.author}}
          </div>
        </el-card>
      </el-tooltip>
<!--  添加图书    -->
      <edit-form @onSubmit="loadBooks()" ref="edit"></edit-form>
    </el-row>
<!--分页-->
    <el-row>
      <el-pagination
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :page-sizes="[10, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="books.length">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
import EditForm from './EditForm'
import SearchBar from './SearchBar'
export default {
  name: 'Books',
  components: {SearchBar, EditForm},
  data () {
    return {
      books: [],
      currentPage: 1,
      pageSize: 10
    }
  },
  mounted () {
    this.loadBooks()
  },
  methods: {
    loadBooks () {
      var _this = this
      this.$axios.get('/books').then(resp => {
        if (resp && resp.status === 200) {
          _this.books = resp.data
        }
      })
    },
    editBook (item) {
      this.$refs.edit.dialogFormVisible = true
      this.$refs.edit.form = {
        id: item.id,
        title: item.title,
        author: item.author,
        date: item.date,
        press: item.press,
        cover: item.cover,
        abs: item.abs,
        category: {
          id: item.category.id,
          name: item.category.name
        }
      }
    },
    searchResult () {
      var _this = this
      this.$axios.get('/books/search?keywords=' + this.$refs.search.keywords)
        .then(resp => {
          if (resp && resp.status === 200) {
            _this.books = resp.data
          }
        })
    },
    deleteBook (id) {
      this.$confirm('此操作将永久删除该书籍, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('/books/' + id)
          .then(resp => {
            if (resp && resp.status === 200) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.loadBooks()
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage)
    },
    handleSizeChange: function (pageSize) {
      this.pageSize = pageSize
    }
  }
}
</script>

<style scoped>
  .cover {
    width: 115px;
    height: 172px;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }

  img {
    width: 115px;
    height: 172px;
    /*margin: 0 auto;*/
  }

  .title {
    font-size: 14px;
    text-align: left;
  }

  .author {
    color: #333;
    width: 102px;
    font-size: 13px;
    margin-bottom: 6px;
    text-align: left;
  }

  .abstract {
    display: block;
    line-height: 17px;
  }

  a {
    text-decoration: none;
  }

  a:link, a:visited, a:focus {
    color: #3377aa;
  }
</style>
