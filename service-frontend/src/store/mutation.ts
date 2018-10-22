export default {
    cleanFlashMessage(state: any) {
        state.flashMessage = ''
    },

    setFlashMessage(state: any, message: string) {
        state.flashMessage = message
    },

    logout(state: any) {
        state.username = ''
    },

    login(state: any, username: string) {
        state.username = username
    },

    changePath(state: any, path: string) {
        state.path = path
    }
}
