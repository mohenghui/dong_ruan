<!-- 模板，div ，el ui元素布局-->
<template>
<!-- 一个根DIV -->
  <div>
    <!-- 公共头部 -->
     <Header></Header>
    <!-- 卡片视图 -->
    <el-card>
       <!-- 2.1 顶部操作 -->

      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 使用clearable属性即可得到一个可清空的输入框   clear	在点击由 clearable 属性生成的清空按钮时触发-->
          <el-input placeholder="请输入内容" v-model="queryInfo.query" class="input-with-select" clearable @clear="getList">
            <template #append>
              <el-button icon="el-icon-search" @click="getList"></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialog">添加</el-button>
          <el-button type="primary">批量删除</el-button>
        </el-col>
      </el-row>



      <!-- 2.2 列表区域 -->
      <el-table :data="list" border stripe>
        <!-- 索引列 -->
        <el-table-column type="index" label="序号" width="60"></el-table-column>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="摘要" prop="description"></el-table-column>
        <el-table-column label="内容" prop="content"></el-table-column>
        <el-table-column label="发表时间" prop="created"></el-table-column>
        <!-- 自定义列 -->
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.id)">编辑
            </el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeById(scope.row.id)">删除
            </el-button>
            <!-- {{scope.row}} -->
          </template>
        </el-table-column>
      </el-table>

      <!-- 2.3 分页 -->

      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum" :page-size="queryInfo.pagesize" :page-sizes="[5, 10, 20, 40]"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>

     <!-- 3.弹框区域 -->

    <!-- 添加框 -->
    <el-dialog title="添加" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form :model="addForm" ref="addFormRef" :rules="addFormRules" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="addForm.title"></el-input>
        </el-form-item>
        <el-form-item label="摘要" prop="description">
          <el-input v-model="addForm.description"></el-input>
        </el-form-item>
          <el-form-item label="内容" prop="content">
          <el-input type="textarea" v-model="addForm.content"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑框 -->
    <el-dialog title="编辑角色" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form :model="editForm" ref="editFormRef" :rules="editFormRules" label-width="100px">
         <el-form-item label="标题" prop="title">
          <el-input v-model="editForm.title"></el-input>
        </el-form-item>
        <el-form-item label="摘要" prop="description">
          <el-input v-model="editForm.description"></el-input>
        </el-form-item>
          <el-form-item label="内容" prop="content">
          <el-input type="textarea" v-model="editForm.content"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
    </el-dialog>

      <!-- 公共底部 -->
      <Footer></Footer>
  </div>
</template>

<!-- 行为，业务逻辑 -->
<script>
//导入组件
import Header from "../components/Header";
import Footer from "../components/Footer";

export default {
  //注册
  components: {Header,Footer},

  //变量，配合ElementUI使用
  data() {
    return {
         /// ////////////////////列表 el-table
        list: [],

        /// ////////////////////分页查询
        queryInfo: {
          query: '',
          pagenum: 1, // 当前的页数
          pagesize: 5 // 当前每页显示多少条数据
        },
        total: 0,

        /// ////////////////////添加框  form，验证
        addDialogVisible: false,
        addForm: {
          title: '',
          description: '',
          content: ''
        },
        addFormRules: {
          title: [
            { required: true, message: '请输入标题', trigger: 'blur' },
            { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '请输入摘要', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '请输入内容', trigger: 'blur' }
          ]
        },

        /// ////////////////////修改框  form，验证
        editForm: {},
        editDialogVisible: false,
        editFormRules: {
          title: [
            { required: true, message: '请输入标题', trigger: 'blur' },
            { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '请输入摘要', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '请输入内容', trigger: 'blur' }
          ]
        }
    };
  },
  //页面初始化加载，生命周期函数
  created() {
     this.getList()
  },
  //自定义函数，操作变量
  methods: {
        /// //////////////////// 获取所有角色的列表
      async getList() {
        const {
          data: res
        } = await this.$http.post('/blog/list', this.$qs.stringify(this.queryInfo))
        console.log(res)
        if (res.code !== 200) {
          return this.$message.error('获取列表失败！')
        }
        this.list = res.data.records
        this.total=res.data.total
      },

      /// ////////////////////分页方法
      // 监听 pagesize 改变的事件
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`)
        this.queryInfo.pagesize = val
        this.getList()
      },

      // 监听 页码值 改变的事件
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`)
        this.queryInfo.pagenum = val // 获取新页码
        this.getList()
      },

      /// ////////////////////添加功能
      addDialog() {
        this.addDialogVisible = true
      },
      addDialogClosed() {
        this.$refs.addFormRef.resetFields()
      },
      add() {
        this.$refs.addFormRef.validate(async valid => {
          if (!valid) return
          // 可以发起添加用户的网络请求
          const {
            data: res
          } = await this.$http.post('/blog/save', this.addForm)

          if (res.code!== 200) {
           return this.$message.error('添加信息失败！')
          }

          this.$message.success('添加信息成功！')
          this.addDialogVisible = false
          this.getList()
        })
      },

      /// ////////////////////编辑功能
      async showEditDialog(id) {
        console.log(id)
        const {
          data: res
        } = await this.$http.get('/blog/info/' + id)
        if (res.code !== 200) {
          return this.$message.error('查询信息失败！')
        }
        console.log(res.data)
        this.editForm = res.data // 对象复制
        this.editDialogVisible = true
      },
      editDialogClosed() {
        this.$refs.editFormRef.resetFields()
      },
      update() {
        this.$refs.editFormRef.validate(async valid => {
          if (!valid) return
          //
          const {
            data: res
          } = await this.$http.post(
            '/blog/update', this.editForm
          )
          if (res.code !== 200) {
            return this.$message.error('更新信息失败！')
          }

          // 提示修改成功
          this.$message.success('更新信息成功！')
          // 关闭对话框
          this.editDialogVisible = false
          // 刷新数据列表
          this.getList()
        })
      },

      /// ////////////////////删除功能   ，实现批量删除
      removeById(id) {
        // 弹框
        this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          // 删除逻辑
          const {
            data: res
          } = await this.$http.post('/blog/delete',[id])

          if (res.code !== 200) {
            return this.$message.error('删除信息失败！')
          }

          this.$message.success('删除信息成功！')

          this.getList()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }
  },
};
</script>

<!-- css,样式 ，scoped 本页CSS有效，生命同名class <el-card> ==> <div class="el-card">  -->
<style  scoped>
.el-card {
    margin-top: 15px;
  }

.el-table {
  margin-top: 15px;
  margin-bottom: 15px;
 }
</style>
