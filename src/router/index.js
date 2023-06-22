import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/admin',
    component: () => import('../views/admin/Admin.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '', // 默认子路由
        name: 'AdminDashboard',
        component: () => import('../views/admin/AdminDashboard.vue'),
      },
      {
        path: '/admin/student-management',
        name: 'StudentManagement',
        component: () => import('../views/admin/StudentManagement.vue'),
        // meta: { requiresAuth: true }
      },
      {
        path: 'scholarship-types',
        name: 'ScholarshipTypes',
        component: () => import('../views/admin/ScholarshipTypes.vue'),
        // meta: { requiresAuth: true }
      },
      {
        path: 'scholarship-applications',
        name: 'ScholarshipApplications',
        component: () => import('../views/admin/ScholarshipApplications.vue'),
        // meta: { requiresAuth: true }
      },
      {
        path: 'evaluation-results',
        name: 'EvaluationResults',
        component: () => import('../views/admin/EvaluationResults.vue'),
        // meta: { requiresAuth: true }
      },
      {
        path: 'admin-management',
        name: 'AdminManagement',
        component: () => import('../views/admin/AdminManagement.vue'),
        // meta: { requiresAuth: true }
      }
    ]
  },
  {
    path: '/student',
    name: 'Student',
    component: () => import('../views/student/Student.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '', // 当访问 /student 时
        redirect: '/student/profile' // 重定向到 /student/profile
      },
      {
        path: 'profile',
        name: 'StudentProfile',
        component: () => import('../views/student/StudentProfile.vue')
      },
      {
        path: 'scholarship-projects',
        name: 'ScholarshipProjects',
        component: () => import('../views/student/ScholarshipProjects.vue')
      },
      {
        path: 'scholarship-application',
        name: 'ScholarshipApplication',
        component: () => import('../views/student/ScholarshipApplication.vue')
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
