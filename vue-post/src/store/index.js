import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

const state = {
    // 用户信息
    user: ""
}

const mutations = {
    setUser(state, user) {
        state.user = user
    }
}

const actions = {}

const plugins = [
    // 持久化state
    createPersistedState({
        storage: window.sessionStorage
    })
]

export default new Vuex.Store({
    state,
    mutations,
    actions,
    plugins
})
