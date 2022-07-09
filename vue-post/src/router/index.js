import VueRouter from "vue-router"
import Account from "../views/account"
import Post from "../views/post"
import Category from "../views/category"
import Edit from "../views/post/edit"
import Panel from "../views/panel"
import Login from "../views/register/login"
import Register from "../views/register/register"
import E404 from "../views/error/404"
import ERROR from "../views/error/500"
import Layout from "../layout"
import Log from "../views/log"
import User from "../views/user"
import Messages from "../views/message"
import { Message } from "element-ui"
import store from "../store"

let router = new VueRouter({
    routes: [
        {
            name: "Home",
            path: "/",
            component: Layout,
            redirect: "/post",
            children: [
                {
                    path: "panel",
                    component: Panel
                },
                {
                    path: "post",
                    component: Post
                    // children:[
                    //     {
                    //         name: "Edit",
                    //         path: "edit",
                    //         component: Edit
                    //     }
                    // ]
                },
                {
                    path: "category",
                    component: Category
                },
                {
                    path: "account",
                    component: Account
                },
                {
                    path: "log",
                    component: Log
                },
                {
                    path: "user",
                    component: User
                },
                {
                    path: "message",
                    component: Messages
                }
            ]
        },
        {
            path: "/404",
            component: E404
        },
        {
            path: "/500",
            component: ERROR
        },
        {
            path: "/login",
            component: Login,
        },
        {
            path: "/register",
            component: Register
        },
        {
            name: "Edit",
            path: "/edit",
            component: Edit,
            props: true
        },
        {
            path: "*",
            redirect: "/404",
            hidden: true
        }
    ]

})

router.beforeEach((to, from, next) => {
    if (to.path === "/login" || to.path === "/register" || to.path === "/404") {
        next();
    } else {
        const user = store.state.user.userCode
        if (user === null || user === "" || user === undefined) {
            Message.error("请先登录！");
            router.push({
                path: "/login",
            });
        } else if (to.path === "/user") {
            const state = store.state.user.state
            if (state === "9") {
                next()
            } else {
                Message.error("权限不足！");
            }
        } else {
            next()
        }
    }

})

export default router;