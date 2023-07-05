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
        redirect: '/admin/student-management' // 重定向到
      },
      {
        path: '/admin/student-management',
        name: 'StudentManagement',
        component: () => import('../views/admin/StudentManagement.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'scholarship-applications',
        name: 'ScholarshipApplications',
        component: () => import('../views/admin/ScholarshipApplications.vue'),
        meta: { requiresAuth: true }
      },
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
        redirect: '/student/scholarship-application' // 重定向到
      },
      {
        path: 'scholarship-application',
        name: 'ScholarshipApplication',
        component: () => import('../views/student/ScholarshipApplication.vue'),
        meta: { requiresAuth: true }
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});


export default router;
