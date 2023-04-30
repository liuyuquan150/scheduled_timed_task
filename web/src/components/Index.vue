<script setup lang="ts">
    import {onMounted, ref} from 'vue'
    import {ElMessage, ElMessageBox} from "element-plus"
    import axios from "axios"

    const dialogVisible = ref<boolean>(false)
const dialogTitle = ref<string>('')

interface TimedTask {
    executorName: string,
    taskName: string,
    taskParameters: string,
    cronExpression: string,
    taskStatus: number,
    remark: string
}

const task = ref<TimedTask>({
    executorName: '',
    taskName: '',
    taskParameters: '',
    cronExpression: '',
    taskStatus: 1,
    remark: ''
})

let tasks = ref<Array<TimedTask>>([])

/**
 * onMounted 相当于 Vue 2 的 mounted.
 */
onMounted(() => {
    searchAllTimedTasks()
})

/**
 * 显示 "添加任务" 视图.
 */
const showAddTaskView = () => {
    task.value = {
        cronExpression: "", executorName: "", remark: "", taskName: "", taskParameters: "", taskStatus: 1
    }
    dialogTitle.value = '添加作业'
    dialogVisible.value = true
}

/**
 * 显示 "修改任务" 视图.
 */
const showUpdateTaskView = (taskData: TimedTask) => {
    Object.assign(task.value, taskData);
    dialogTitle.value = '修改作业'
    dialogVisible.value = true
}

/**
 * 提交视图(添加/修改)中的定时任务数据.
 */
const commitTimedTaskData = () => {
    task.value["id"] ? updateTimedTask() : addTimedTask()
}

/**
 * 添加定时任务.
 */
const addTimedTask = () => {
    axios.post('http://localhost:8079/api/v1/task', task.value).then(response => {
        if (response.status === 200) {
            const {success, message} = response.data
            ElMessage({
                type: success ? 'success' : 'error',
                message,
                showClose: true
            })
            dialogVisible.value = false;
            searchAllTimedTasks();
        }
    });
}

/**
 * 搜索所有定时任务.
 */
const searchAllTimedTasks = () => {
    axios.get('http://localhost:8079/api/v1/tasks').then(response => {
        if (response.status === 200) {
            const {success, code, data} = response.data
            if (success && code === 200) {
                tasks.value = data
            }
        }
    });
}

/**
 * 修改定时任务信息.
 */
const updateTimedTask = () => {
    axios.put('http://localhost:8079/api/v1/task', task.value).then(response => {
        if (response.status === 200) {
            const {success, message} = response.data
            ElMessage({
                type: success ? 'success' : 'error',
                message,
                showClose: true
            })
            dialogVisible.value = false;
            searchAllTimedTasks();
        }
    });
}

/**
 * 运行或停止定时任务.
 *
 * @param taskData 被运行或停止的定时任务.
 */
const runOrStopTimedTask = (taskData: TimedTask) => {
    axios.put('http://localhost:8079/api/v1/task', taskData).then(response => {
        if (response.status === 200) {
            const {success, message} = response.data
            ElMessage({
                type: success ? 'success' : 'error',
                message,
                showClose: true
            })
        }
    });
}

/**
 * 错误定时任务.
 *
 * @param taskData 被删除的定时任务.
 */
const deleteTask = (taskData: TimedTask) => {
    ElMessageBox.confirm(
        `此操作将永久删除编号为[${taskData["id"]}]的任务, 是否继续?`,
        'Warning',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        axios.delete(`http://localhost:8079/api/v1/task/${taskData["id"]}`).then(response => {
            if (response.status === 200) {
                const {success, message} = response.data
                ElMessage({
                    type: success ? 'success' : 'error',
                    message,
                    showClose: true
                })
                searchAllTimedTasks();
            }
        })
    }).catch(() => {
        ElMessage({
            type: 'success',
            message: '操作取消成功!',
            showClose: true
        })
    })
}
</script>

<template>
      <el-table :data="tasks" style="width: 100%" border max-height="250">
          <el-table-column cenfixed prop="id" label="任务编号" width="130" header-align="center"></el-table-column>
          <el-table-column prop="executorName" label="任务执行者名称" width="130" header-align="center"></el-table-column>
          <el-table-column prop="taskName" label="任务名称" width="130" header-align="center"></el-table-column>
          <el-table-column prop="taskParameters" label="任务参数" width="130" header-align="center"></el-table-column>
          <el-table-column prop="taskParameters" label="Cron表达式" width="130" header-align="center">
              <template #default="scope">
                  <el-tag>{{ scope.row.cronExpression }}</el-tag>
              </template>
          </el-table-column>
          <el-table-column prop="taskStatus" label="任务状态" width="150">
              <template #default="scope">
                  <!-- 如果后端返回的值既不等于 :inactive-value, 也不等于 :active-value, 那么在页面初始化时会自动执行一次 @change -->
                  <el-switch
                          v-model="scope.row.taskStatus"
                          @change="runOrStopTimedTask(scope.row)"
                          inactive-text="停止"
                          :inactive-value="0"
                          active-text="运行"
                          :active-value="1"
                          active-color="#13ce66"
                          inactive-color="#ff4949">
                  </el-switch>
              </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注信息" width="130" header-align="center"></el-table-column>
          <el-table-column prop="gmtCreate" label="创建时间" width="170" header-align="center"></el-table-column>
          <el-table-column prop="gmtModified" label="更新时间" width="170" header-align="center"></el-table-column>
          <el-table-column fixed="right" label="操作" header-align="center">
              <template #default="scope">
                  <el-button
                          type="primary"
                          size="small"
                          @click.prevent="showUpdateTaskView(scope.row)"
                  >
                      编辑任务
                  </el-button>
                  <el-button
                          type="danger"
                          size="small"
                          @click.prevent="deleteTask(scope.row)"
                  >
                      删除任务
                  </el-button>
              </template>
          </el-table-column>
      </el-table>
      <el-button class="mt-4" style="width: 100%" @click="showAddTaskView">添加任务</el-button>
      <el-dialog v-model="dialogVisible" :title="dialogTitle" width="30%" center>
          <el-form ref="formRef" label-width="140px">
              <el-form-item label="任务执行者名称" required>
                  <el-input v-model="task.executorName"></el-input>
              </el-form-item>
              <el-form-item label="任务名称" required>
                  <el-input v-model="task.taskName"></el-input>
              </el-form-item>
              <el-form-item label="任务参数">
                  <el-input v-model="task.taskParameters"></el-input>
              </el-form-item>
              <el-form-item label="Cron表达式" required>
                  <el-input v-model="task.cronExpression"></el-input>
              </el-form-item>
              <el-form-item label="作业状态">
                  <el-radio v-model="task.taskStatus" :label="0">停止</el-radio>
                  <el-radio v-model="task.taskStatus" :label="1">运行</el-radio>
              </el-form-item>
              <el-form-item label="备注信息">
                  <el-input v-model="task.remark"></el-input>
              </el-form-item>
          </el-form>
          <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="commitTimedTaskData">确 定</el-button>
        </span>
          </template>
      </el-dialog>
</template>
