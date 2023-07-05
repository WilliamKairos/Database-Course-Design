<template>
  <div class="container">
    <el-table :data="students" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="学生姓名">
        <template #default="{ row }">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="studentId" label="学号"></el-table-column>
      <el-table-column prop="academicScore" label="学业成绩"></el-table-column>
      <el-table-column prop="ideologyScore" label="思政表现"></el-table-column>
      <el-table-column prop="researchScore" label="科研能力"></el-table-column>
      <el-table-column prop="socialScore" label="社会服务"></el-table-column>
      <el-table-column label="总分">
        <template #default="{ row }">
          <span>{{ calculateTotalScore(row) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="totalPoints" label="积分"></el-table-column>
      <el-table-column prop="scholarshipType" label="奖学金种类"></el-table-column>
      <el-table-column prop="academicEvaluation" label="教务评定"></el-table-column>
      <el-table-column prop="applicationTime" label="申请时间"></el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="danger" size="mini" @click="deleteStudent(row)">删除</el-button>
          <el-button type="primary" size="mini" @click="showApprovalDialog(row)">审批</el-button>
          <el-button type="success" size="mini" @click="viewAttachments(row)">查看</el-button>
          <el-button type="warning" size="mini" @click="openScoreDialog(row)">评分</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30]"
        :page-size="pageSize"
        layout="prev, pager, next, jumper, total, sizes"
        :total="studentsTotal"
    ></el-pagination>
    <div class="button-group">
      <el-button type="text" @click="fetchStudents('grade1')">研一年级</el-button>
      <el-button type="text" @click="fetchStudents('grade2')">研二年级</el-button>
      <el-button type="text" @click="fetchStudents('grade3')">研三年级</el-button>
    </div>
    <el-dialog v-model="addStudentDialogVisible" title="审批" width="30%">
      <el-form>
        <el-form-item label="姓名">
          <el-input v-model="newStudent.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="newStudent.studentId" disabled></el-input>
        </el-form-item>
        <el-form-item label="审批状态">
          <el-radio-group v-model="newStudent.approvalStatus">
            <el-radio label="通过">通过</el-radio>
            <el-radio label="不通过">不通过</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="不通过理由" v-show="newStudent.approvalStatus === '不通过'">
          <el-input v-model="newStudent.reason" placeholder="请输入不通过理由"></el-input>
        </el-form-item>
        <div style="text-align: center">
          <el-button type="primary" @click="submitApproval">确定</el-button>
          <el-button @click="cancelApproval">取消</el-button>
        </div>
      </el-form>
    </el-dialog>
    <el-dialog v-model="deleteConfirmationVisible" title="确认删除" width="30%">
      <span>确定要删除该学生吗？</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="deleteConfirmationVisible = false">取消</el-button>
      <el-button type="primary" @click="deleteStudentConfirmed">确认</el-button>
    </span>
    </el-dialog>
    <el-dialog v-model="scoreDialogVisible" title="评分" width="30%">
      <el-form>
        <el-form-item label="学业成绩">
          <el-input-number v-model="currentStudent.academicScore" :disabled="!currentStudent.academicScoreEditable" :min="0" :max="20" @input="handleScoreInput('academicScore', $event)"></el-input-number>
        </el-form-item>
        <el-form-item label="思政表现">
          <el-input-number v-model="currentStudent.ideologyScore" :min="0" :max="30" @input="handleScoreInput('ideologyScore', $event)"></el-input-number>
        </el-form-item>

        <el-form-item label="科研能力">
          <el-input-number v-model="currentStudent.researchScore" :min="0" :max="currentStudent.grade === 'grade3' ? 50 : 30" @input="handleScoreInput('researchScore', $event)"></el-input-number>
        </el-form-item>

        <el-form-item label="社会服务">
          <el-input-number v-model="currentStudent.socialScore" :min="0" :max="20" @input="handleScoreInput('socialScore', $event)"></el-input-number>
        </el-form-item>

        <div style="text-align: center">
          <el-button type="primary" @click="submitScores">确定</el-button>
          <el-button @click="scoreDialogVisible = false">取消</el-button>
        </div>
      </el-form>
    </el-dialog>
    <el-dialog v-model="attachmentDialogVisible" title="附件" width="80%">
      <el-table :data="papers" style="width: 100%">
        <el-table-column prop="level" label="论文级别"></el-table-column>
        <el-table-column prop="journal" label="发表杂志"></el-table-column>
        <el-table-column prop="title" label="论文名称"></el-table-column>
        <el-table-column prop="points" label="积分"></el-table-column>
      </el-table>
      <el-table :data="competitions" style="width: 100%">
        <el-table-column prop="level" label="竞赛级别"></el-table-column>
        <el-table-column prop="name" label="竞赛名称"></el-table-column>
        <el-table-column prop="awardLevel" label="获奖级别"></el-table-column>
        <el-table-column prop="points" label="积分"></el-table-column>
      </el-table>
      <div style="text-align: center">
        <el-button type="primary" @click="currentStudent.downloadAttachments">下载附件</el-button>

      </div>
    </el-dialog>

  </div>
</template>

<script>
import { ref, reactive, onMounted, watch, onBeforeUnmount } from 'vue';

import {
  ElTable,
  ElTableColumn,
  ElButton,
  ElPagination,
  ElDialog,
  ElForm,
  ElFormItem,
  ElInput,
  ElInputNumber,
  ElMessage,
  ElMessageBox
} from 'element-plus';
import axios from 'axios';

export default {
  components: {
    ElTable,
    ElTableColumn,
    ElButton,
    ElPagination,
    ElDialog,
    ElForm,
    ElFormItem,
    ElInput,
    ElInputNumber,
    ElMessageBox,
  },
  name: 'StudentList',
  setup() {
    const students = ref([]);
    const studentsTotal = ref(0);
    const loading = ref(false);
    const currentPage = ref(1);
    const pageSize = ref(10);
    const addStudentDialogVisible = ref(false);
    const newStudent = reactive({
      name: '',
      studentId: '',
      academicScore: 0,
      ideologyScore: 0,
      researchScore: 0,
      socialScore: 0,
      scholarshipType: '',
      academicEvaluation: '',
      ranking: '',
      applicationTime: '',
    });
    const papers = ref([]);
    const competitions = ref([]);
    const attachmentDialogVisible = ref(false);


    const scoreDialogVisible = ref(false);
    const currentStudent = reactive({
      name: '',
      academicScore: 0,
      ideologyScore: 0,
      researchScore: 0,
      socialScore: 0,
    });
    const handleScoreInput = (field, value) => {
      // 根据字段获取对应的分数上限
      let scoreLimit = 0;
      if (field === 'academicScore') {
        scoreLimit = currentStudent.grade === 'grade2' ? 20 : 0;
      } else if (field === 'ideologyScore') {
        scoreLimit = 30;
      } else if (field === 'researchScore') {
        scoreLimit = currentStudent.grade === 'grade3' ? 50 : 30;
      } else if (field === 'socialScore') {
        scoreLimit = 20;
      }

      // 根据分数上限对输入的数值进行限制和修改
      if (value < 0 || isNaN(value)) {
        currentStudent[field] = 0;
      } else if (value > scoreLimit) {
        currentStudent[field] = scoreLimit;
      } else {
        currentStudent[field] = value;
      }
    };

    const openScoreDialog = (student) => {
      currentStudent.name = student.name;
      currentStudent.studentId = student.studentId; // 设置当前学生的学号
      currentStudent.grade = student.grade; // 设置当前学生的年级信息

      // 根据学生年级设置评分上限和学习成绩是否可修改
      if (student.grade === 'grade2') {
        currentStudent.academicScore = Math.min(student.academicScore, 20);
        currentStudent.ideologyScore = Math.min(student.ideologyScore, 30);
        currentStudent.researchScore = Math.min(student.researchScore, 30);
        currentStudent.socialScore = Math.min(student.socialScore, 20);
        currentStudent.academicScoreEditable = true; // 学习成绩不可修改
      } else if (student.grade === 'grade3') {
        currentStudent.academicScore = 0; // 学习成绩默认为0分
        currentStudent.ideologyScore = Math.min(student.ideologyScore, 30);
        currentStudent.researchScore = Math.min(student.researchScore, 50);
        currentStudent.socialScore = Math.min(student.socialScore, 20);
        currentStudent.academicScoreEditable = false; // 学习成绩可修改
      }

      scoreDialogVisible.value = true;
    };


    const submitScores = () => {
      // 根据学生年级设置分数上限
      const grade = currentStudent.grade;
      let academicScoreLimit = 0;
      let ideologyScoreLimit = 0;
      let researchScoreLimit = 0;
      let socialScoreLimit = 0;

      if (grade === 'grade2') {
        academicScoreLimit = 20;
        ideologyScoreLimit = 30;
        researchScoreLimit = 30;
        socialScoreLimit = 20;
      } else if (grade === 'grade3') {
        academicScoreLimit = 0; // 学业成绩默认为0分
        ideologyScoreLimit = 30;
        researchScoreLimit = 50;
        socialScoreLimit = 20;
      }

      // 限制分数上限
      currentStudent.academicScore = Math.min(currentStudent.academicScore, academicScoreLimit);
      currentStudent.ideologyScore = Math.min(currentStudent.ideologyScore, ideologyScoreLimit);
      currentStudent.researchScore = Math.min(currentStudent.researchScore, researchScoreLimit);
      currentStudent.socialScore = Math.min(currentStudent.socialScore, socialScoreLimit);

      axios
          .post('http://localhost:8080/api/applicants/scores', {
            studentId: currentStudent.studentId,
            academicScore: currentStudent.academicScore,
            ideologyScore: currentStudent.ideologyScore,
            researchScore: currentStudent.researchScore,
            socialScore: currentStudent.socialScore,
          })
          .then(() => {
            // 更新学生列表中对应学生的分值字段
            students.value = students.value.map((student) => {
              if (student.studentId === currentStudent.studentId) {
                return {
                  ...student,
                  academicScore: currentStudent.academicScore,
                  ideologyScore: currentStudent.ideologyScore,
                  researchScore: currentStudent.researchScore,
                  socialScore: currentStudent.socialScore,
                };
              }
              return student;
            });

            scoreDialogVisible.value = false;
          })
          .catch((error) => {
            console.error(error);
            // 处理错误，显示错误消息或执行其他必要的操作
          });
    };


    const showApprovalDialog = (student) => {
      newStudent.name = student.name; // 设置审批对话框中的学生姓名
      newStudent.studentId = student.studentId; // 设置审批对话框中的学生学号
      newStudent.approvalStatus = ''; // 重置审批状态
      newStudent.reason = ''; // 重置不通过理由
      addStudentDialogVisible.value = true; // 显示审批对话框
    };

    const viewAttachments = (student) => {
      papers.value = student.papers;
      competitions.value = student.competitions;
      attachmentDialogVisible.value = true; // Show the dialog for viewing attachments

      // Add the logic for downloading attachments
      // const downloadAttachments = () => {
      //   const attachmentUrls = JSON.parse(student.applicationMaterial || '[]');
      //   attachmentUrls.forEach((url) => {
      //     // Trigger the download for each attachment URL
      //     const link = document.createElement('a');
      //     link.href = url;
      //     link.download = url.substring(url.lastIndexOf('/') + 1);
      //     link.target = '_blank';
      //     link.rel = 'noopener noreferrer';
      //     link.click();
      //   });
      // };
      const downloadAttachments = () => {
        const attachmentUrls = JSON.parse(student.applicationMaterial || '[]');

        attachmentUrls.forEach((url, index) => {
          setTimeout(() => { // use setTimeout to introduce delay
            // Trigger the download for each attachment URL
            const link = document.createElement('a');
            link.href = url;
            link.download = url.substring(url.lastIndexOf('/') + 1);
            link.target = '_blank';
            link.rel = 'noopener noreferrer';
            link.click();
          }, 2000 * index); // delay of 2 seconds for each download
        });
      };

      // Add the downloadAttachments method to the current context
      currentStudent.downloadAttachments = downloadAttachments;
    };

    const submitApproval = () => {
      if (newStudent.approvalStatus === '不通过' && !newStudent.reason) {
        ElMessage.error('请输入不通过理由');
        return;
      }

      axios
          .post('http://localhost:8080/api/applicants/approval', {
            studentId: newStudent.studentId,
            academicEvaluation: newStudent.approvalStatus,
            reason: newStudent.reason,
          })
          .then(() => {
            // 重置审批对话框字段
            // newStudent.approvalStatus = '';
            // newStudent.reason = '';
            addStudentDialogVisible.value = false; // 关闭审批对话框

            // 更新学生列表中对应学生的教务评定字段
            const updatedStudents = students.value.map((student) => {
              if (student.studentId === newStudent.studentId) {
                return {
                  ...student,
                  academicEvaluation: newStudent.approvalStatus,
                };
              }
              return student;
            });

            students.value = updatedStudents; // 更新学生列表
          })
          .catch((error) => {
            console.error(error);
            ElMessage.error('提交审批失败');
          });
    };


    const cancelApproval = () => {
      // Reset the approval dialog fields
      newStudent.approvalStatus = '';
      newStudent.rejectionReason = '';
      addStudentDialogVisible.value = false; // Close the approval dialog
    };


    const selectedStudents = ref([]);

    const deleteConfirmationVisible = ref(false);

    const fetchStudents = async (grade) => {
      try {
        loading.value = true;
        const decodedGrade = decodeURIComponent(grade); // Decode the grade
        const response = await axios.post('http://localhost:8080/api/applicants', { grade: decodedGrade });
        students.value = response.data.data.map((student) => ({
          ...student,
          academicScore: student.academicScore || 0,
          ideologyScore: student.ideologyScore || 0,
          researchScore: student.researchScore || 0,
          socialScore: student.socialScore || 0,
          papers: JSON.parse(student.papers || '[]'),
          competitions: JSON.parse(student.competitions || '[]'),
        }));

        studentsTotal.value = response.data.total;
      } catch (error) {
        console.error(error);
      } finally {
        loading.value = false;
      }
    };

    // Remove the existing `watch` import statement from the `setup` function

    const deleteStudent = async (student) => {
      try {
        await ElMessageBox.confirm('确定要删除该学生吗？', '确认删除', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        });

        // 执行删除操作
        await axios.delete(`http://localhost:8080/api/applicants/${student.studentId}`);

        // 更新学生列表
        students.value = students.value.filter((s) => s.studentId !== student.studentId);
      } catch (error) {
        console.error(error);
      }
    };

    const deleteStudentConfirmed = async () => {
      deleteConfirmationVisible.value = false; // Close the delete confirmation dialog

      // Perform the deletion logic
      if (selectedStudents.value.length > 0) {
        try {
          const selectedIds = selectedStudents.value.map((student) => student.studentId);
          await Promise.all(
              selectedIds.map((id) => axios.delete(`http://localhost:8080/api/applicants/${id}`))
          );
          students.value = students.value.filter(
              (student) => !selectedIds.includes(student.studentId)
          );
        } catch (error) {
          console.error(error);
        }
      }

      selectedStudents.value = []; // Clear the selected students array
    };

    const updateStudentScores = async (studentId, academicScore, ideologyScore, researchScore, socialScore) => {
      try {
        await axios.post('http://localhost:8080/api/applicants/scores', {
          studentId: studentId,
          academicScore: academicScore,
          ideologyScore: ideologyScore,
          researchScore: researchScore,
          socialScore: socialScore,
        });
      } catch (error) {
        console.error(error);
      }
    };



    const calculateTotalScore = (student) => {
      return (
          student.academicScore +
          student.ideologyScore +
          student.researchScore +
          student.socialScore
      );
    };

    const handleSelectionChange = (selectedItems) => {
      selectedStudents.value = selectedItems;
    };

    const handleSizeChange = (newPageSize) => {
      currentPage.value = 1;
      pageSize.value = newPageSize;
      fetchStudents(); // Fetch students again with the new page size
    };

    const handleCurrentChange = (page) => {
      currentPage.value = page;
      fetchStudents(); // Fetch students again with the new current page
    };

    onMounted(() => {
      fetchStudents('grade1');
    });

    return {
      students,
      studentsTotal,
      loading,
      currentPage,
      pageSize,
      addStudentDialogVisible,
      newStudent,
      selectedStudents,
      deleteConfirmationVisible,
      papers,
      competitions,
      attachmentDialogVisible,
      updateStudentScores,
      deleteStudentConfirmed,
      showApprovalDialog,
      viewAttachments,
      submitApproval,
      cancelApproval,
      fetchStudents,
      deleteStudent,
      calculateTotalScore,
      handleSelectionChange,
      handleSizeChange,
      handleCurrentChange,
      openScoreDialog, // 添加的评分对话框方法
      submitScores, // 添加的提交评分方法
      handleScoreInput,
      scoreDialogVisible, // 添加的评分对话框可见性变量
      currentStudent, // 添加的当前学生评分变量
    };
  },
};
</script>

<style>
/* You can add some styles here to improve the appearance of the page */
</style>

