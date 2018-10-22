import Vue from 'vue'
import Vuex from 'vuex'

import mutations from '@/store/mutation.ts'
import actions from '@/store/action.ts'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
      username: '', // indicates user is logged in
        flashMessage: '',
        path: '',
    },
    getters: {
        authenticated: (state: any) => {
            return state.username !== ''
        },
      username: (state: any) => {
            return state.username
        },
    },
    mutations,
    actions,
})

