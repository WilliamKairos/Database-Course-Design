<template>
  <div class="application-container">
    <h2>奖学金申请</h2>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
      <el-form-item label="学号" prop="studentId">
        <el-input v-model="form.studentId" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="年级" prop="grade">
        <el-input v-model="form.displayedGrade" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="专业" prop="major">
        <el-input v-model="form.major" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="奖学金种类" prop="scholarshipType">
        <el-select v-model="form.scholarshipType" placeholder="请选择">
          <el-option label="国家奖学金" value="国家奖学金"></el-option>
          <el-option label="省级奖学金" value="省级奖学金"></el-option>
          <el-option label="校级奖学金" value="校级奖学金"></el-option>
        </el-select>
      </el-form-item>
      <!-- 论文表 -->
      <el-form-item label="论文表">
        <el-table :data="form.papers" style="width: 100%">
          <el-table-column label="论文级别">
            <template #default="scope">
              <el-select v-model="scope.row.level" placeholder="请选择" @change="updatePoints(scope.row)">
                <el-option label="一类论文" value="一类论文"></el-option>
                <el-option label="二类论文" value="二类论文"></el-option>
                <el-option label="三类论文" value="三类论文"></el-option>
                <el-option label="四类论文" value="四类论文"></el-option>
                <el-option label="五类论文" value="五类论文"></el-option>
                <el-option label="六类论文" value="六类论文"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="发表杂志">
            <template #default="scope">
              <el-input v-model="scope.row.journal" placeholder="请输入"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="论文名称">
            <template #default="scope">
              <el-input v-model="scope.row.title" placeholder="请输入"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="积分">
            <template #default="scope">
              <el-input v-model="scope.row.points" :disabled="true"></el-input>
            </template>
          </el-table-column>
        </el-table>
        <div class="buttons">
          <el-button size="small" type="primary" @click="addPaper">添加</el-button>
          <el-button size="small" type="success" @click="showDetails('paper')">详情</el-button>
        </div>
      </el-form-item>

      <!-- 竞赛表 -->
      <el-form-item label="竞赛表">
        <el-table :data="form.competitions" style="width: 100%">
          <el-table-column label="竞赛级别">
            <template #default="scope">
              <el-select v-model="scope.row.level" placeholder="请选择" @change="updateCompetitionPoints(scope.row)">
                <el-option label="国家级A1" value="国家级A1"></el-option>
                <el-option label="国家级A2" value="国家级A2"></el-option>
                <el-option label="国家级A3" value="国家级A3"></el-option>
                <el-option label="省级A1" value="省级A1"></el-option>
                <el-option label="省级A2" value="省级A2"></el-option>
                <el-option label="省级A3" value="省级A3"></el-option>
                <el-option label="市级A1" value="市级A1"></el-option>
                <el-option label="市级A2" value="市级A2"></el-option>
                <el-option label="市级A3" value="市级A3"></el-option>
                <el-option label="校级" value="校级"></el-option>
                <el-option label="CCF" value="CCF"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="竞赛名称">
            <template #default="scope">
              <el-input v-model="scope.row.name" placeholder="请输入"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="获奖级别">
            <template #default="scope">
              <el-select v-model="scope.row.awardLevel" placeholder="请选择" @change="updateCompetitionPoints(scope.row)">
                <el-option label="一等奖" value="一等奖"></el-option>
                <el-option label="二等奖" value="二等奖"></el-option>
                <el-option label="三等奖" value="三等奖"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="积分">
            <template #default="scope">
              <el-input v-model="scope.row.points" :disabled="true"></el-input>
            </template>
          </el-table-column>
        </el-table>
        <div class="buttons">
          <el-button size="small" type="primary" @click="addCompetition">添加</el-button>
          <el-button size="small" type="success" @click="showDetails('competition')">详情</el-button>
        </div>
      </el-form-item>
      <div class="total-points">总积分: {{ calculateTotalPoints() }}</div>
      <el-form-item label="申请材料" prop="applicationMaterial">
        <el-upload
            action="http://localhost:8080/api/applicants/upload"
            :on-success="handleUploadSuccess"
            :file-list="fileList"
            multiple
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="申请时间" prop="applicationTime">
        <el-date-picker v-model="form.applicationTime" type="date" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="checkGradeAndSubmitForm">提交</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="提示" :visible.sync="showDialog">
      <p>研一年级暂时无法申请奖学金。</p>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted, watchEffect, getCurrentInstance, inject, } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

export default {
  setup() {
    const form = reactive({
      studentId: '',
      name: '',
      grade: '',
      get displayedGrade() {
        switch (this.grade) {
          case 'grade1':
            return '研一年级';
          case 'grade2':
            return '研二年级';
          case 'grade3':
            return '研三年级';
          default:
            return '';
        }
      },
      major: '',
      scholarshipType: '',
      applicationMaterial: [],
      applicationTime: '',
      papers: [], // 论文表数据
      competitions: [], // 竞赛表数据
      totalPoints: 0
    });
    const app = getCurrentInstance();
    const rules = reactive({
      scholarshipType: [{required: true, message: '请选择奖学金种类', trigger: 'change'}],
      applicationTime: [{required: true, message: '请选择申请时间', trigger: 'change'}],
    });
    const formRef = ref();
    const showDialog = ref(false);

    const calculateCompetitionPoints = (competition) => {
      // 计算竞赛积分
      const { level, awardLevel } = competition;
      return competitionPoints[level][awardLevel] || 0;
    };

    const calculateTotalPoints = () => {
      const calculatePoints = (level) => {
        switch (level) {
          case '一类论文':
            return 30;
          case '二类论文':
            return 25;
          case '三类论文':
            return 20;
          case '四类论文':
            return 15;
          case '五类论文':
            return 10;
          case '六类论文':
            return 5;
          default:
            return 0;
        }
      };

      // 计算论文表积分
      let totalPoints = 0;
      if (form.papers) {
        form.papers.forEach((paper) => {
          totalPoints += calculatePoints(paper.level);
        });
      }

      // 计算竞赛表积分
      if (form.competitions) {
        form.competitions.forEach((competition) => {
          totalPoints += calculateCompetitionPoints(competition);
        });
      }

      form.totalPoints = totalPoints;
      return totalPoints;
    };


    watchEffect(() => {
      form.papers.forEach((paper) => {
        updatePoints(paper);
      });
    });

    const calculatePoints = (level) => {
      switch (level) {
        case '一类论文':
          return 30;
        case '二类论文':
          return 25;
        case '三类论文':
          return 20;
        case '四类论文':
          return 15;
        case '五类论文':
          return 10;
        case '六类论文':
          return 5;
        default:
          return 0;
      }
    };

    const addPaper = () => {
      const paper = {
        level: '', // 论文级别
        journal: '', // 发表杂志
        title: '', // 论文名称
        points: 0 // 积分
      };
      form.papers.push(paper);
      updatePoints(paper); // 添加论文后立即更新积分
    };

    const updatePoints = (paper) => {
      paper.points = calculatePoints(paper.level);
      calculateTotalPoints();
    };

    const showDetails = (type) => {
      if (type === 'paper') {
        ElMessage({
          message: '一类论文：CCF推荐A类国际学术期刊/中科院1区\n' +
              '二类论文：CCF推荐B类国际学术期刊和A类国际学术会议/CCF高质量中文期刊T1类/中科院2区\n' +
              '三类论文：CCF推荐的C类国际学术期刊和B类国际学术会议/中科院3区\n' +
              '四类论文：CCF推荐C类国际会议/CCF高质量中文期刊T2类/中科院4区/EI期刊\n' +
              '五类论文：CCF高质量中文期刊T3类/北大中文核心/CSCD中文核心\n' +
              '六类论文：一般学术期刊/国内外学术会议\n' ,

          type: 'info'
        });
      } else if (type === 'competition') {
        ElMessage({
          message: '同一学科竞赛（含晋级赛等），按所有阶段比赛的最高奖项计分，且各阶段的成绩不可叠加;\n' +
              '学科竞赛分团体赛和个人赛，由多人组队参加的团体赛，由组长负责组员分数分配；省级及以下个人赛分值为标准分值*0.8。\n' +
              '学科竟赛若有“特等奖”，按照“特等”、”一等”、“二等”等降序排列，对应评选标准中的“一等”、“二等”和“三等”；\n' +
              '优胜奖”、“参赛奖”等没有获得具体名次学科竞赛，不记入竞赛加分；\n' +
              '不在学校认定的学科竟赛名单中，需经过学校创新学院认定（需有创新学院盖章证明）比赛等级和类别，按照认定后竞赛级别计算分值；\n' +
              '不在学校认定的学科竞赛名单中，且学校创新学院不予认定的比赛，不记入学科竞赛加分。',
          type: 'info'
        });
      }
    };



    const competitionPoints = {
      '国家级A1': { '一等奖': 30, '二等奖': 25, '三等奖': 20 },
      '国家级A2': { '一等奖': 20, '二等奖': 16, '三等奖': 12 },
      '国家级A3': { '一等奖': 15, '二等奖': 12, '三等奖': 9 },
      '省级A1': { '一等奖': 15, '二等奖': 12, '三等奖': 9 },
      '省级A2': { '一等奖': 10, '二等奖': 8, '三等奖': 6 },
      '省级A3': { '一等奖': 8, '二等奖': 6, '三等奖': 4 },
      '市级A1': { '一等奖': 4, '二等奖': 3.5, '三等奖': 3 },
      '市级A2': { '一等奖': 3.5, '二等奖': 3, '三等奖': 2.5 },
      '市级A3': { '一等奖': 3, '二等奖': 2.5, '三等奖': 2 },
      '校级': { '一等奖': 1.5, '二等奖': 1, '三等奖': 0.5 },
      'CCF': { '一等奖': 0.2, '二等奖': 0.2, '三等奖': 0.2 }
    };


    const addCompetition = () => {
      const competition = {
        level: '国家级A1', // Initialize with a default value
        name: '',
        awardLevel: '一等奖', // Initialize with a default value
        points: 0
      };
      if (!form.competitions) {
        form.competitions = [];
      }
      form.competitions.push(competition);
      updateCompetitionPoints(competition);
    };

    const updateCompetitionPoints = (competition) => {
      if (competition.level && competition.awardLevel) { // Only calculate points if level and awardLevel are defined
        competition.points = calculateCompetitionPoints(competition);
        calculateTotalPoints();
      }
    };

    const checkGradeAndShowDialog = () => {
      if (form.grade === '研一年级') {
        showDialog.value = true;
      }
    };
    // 监听 form.grade 变化，变化时检查年级并决定是否弹窗
    watchEffect(checkGradeAndShowDialog);

    const checkGradeAndSubmitForm = () => {
      if (form.grade === '研一年级') {
        showDialog.value = true;
      } else {
        submitForm();
      }
    };

    const fetchStudentInfo = () => {
      const studentId = localStorage.getItem('studentId');
      axios
          .post('http://localhost:8080/api/students/info', {studentId})
          .then((response) => {
            const student = response.data.data;
            form.studentId = student.studentId;
            form.name = student.name;
            form.grade = student.grade;
            form.major = student.major;
            if (student.grade === '研一年级') {
              showDialog.value = true;
            }
          })
          .catch((error) => {
            console.error(error);
          });
    };

    const submitForm = () => {
      formRef.value.validate((valid) => {
        if (valid) {
          // 使用 toLocaleDateString 方法将日期转换为 "yyyy-MM-dd" 格式
          let applicationTimeStr = form.applicationTime.toLocaleDateString('fr-CA');

          const formData = {
            studentId: form.studentId,
            name: form.name,
            grade: form.grade,
            major: form.major,
            scholarshipType: form.scholarshipType,
            applicationMaterial: form.applicationMaterial,
            applicationTime: applicationTimeStr,  // 使用转换后的日期字符串
            papers: form.papers,
            competitions: form.competitions,
            totalPoints: form.totalPoints,
          };

          axios
              // .post('http://localhost:8080/api/applicants/apply', JSON.stringify(data))
              .post('http://localhost:8080/api/applicants/apply', formData)
              .then((response) => {
                if (response.status === 200 && response.data.code === 200) {
                  // 处理成功提交表单的情况
                  // 弹出提示提交成功的弹窗
                  alert("提交成功");
                } else {
                  alert("提交失败");
                }
              })
              .catch((error) => {
                alert("提交失败");
                console.error(error);
              });
        } else {
          console.log('表单校验失败');
          return false;
        }
      });
    };


    const fileList = ref([]);

    const handleUploadSuccess = (response, file) => {
      const responseData = JSON.parse(response.data);
      if (response.code === 200) {
        console.log(responseData);
        if (responseData.filePaths && Array.isArray(responseData.filePaths)) {
          responseData.filePaths.forEach(filePath => {
            console.log(filePath);
            form.applicationMaterial.push(filePath);
          });
        } else if (responseData.filePath) {
          console.log(responseData.filePath);
          form.applicationMaterial.push(responseData.filePath);
        }
        fileList.value.push(file);
      } else {
        console.log(response);
        console.log("Error occurred!");
      }
    };




    // 在组件加载完成后获取学生信息
    onMounted(fetchStudentInfo);

    return {
      form,
      rules,
      formRef,
      showDialog,
      fileList,
      competitionPoints,
      calculateCompetitionPoints,
      addCompetition,
      updateCompetitionPoints,
      calculatePoints,
      updatePoints,
      checkGradeAndSubmitForm,
      checkGradeAndShowDialog,
      fetchStudentInfo,
      submitForm,
      handleUploadSuccess,
      addPaper,
      showDetails,
      calculateTotalPoints
    };
  },
};
</script>

<style>
.application-container {
  margin: 20px;
}

.buttons {
  margin-top: 10px;
}

.total-points {
  margin-top: 20px;
}
</style>
