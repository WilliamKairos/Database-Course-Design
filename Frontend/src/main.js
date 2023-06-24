import { createApp } from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import App from './App.vue';
import router from './router';

const app = createApp(App).use(ElementPlus).use(router);

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!localStorage.getItem('userType')) {
            next({
                path: '/',
                query: { redirect: to.fullPath }
            });
        } else {
            next();
        }
    } else {
        next();
    }
});

app.mount('#app');
// 又没有贡献度？？？