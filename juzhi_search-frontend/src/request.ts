import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000,
  withCredentials: true
})

// 全局请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 在发送请求之前做些什么
    return config
  },
  function(error) {
    // 对请求错误做些什么
    return Promise.reject(error)
  }
)
// 全局响应拦截器
instance.interceptors.response.use(
  (response) => {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    /*    const { data } = response
        if (data.code === 200) {
          if(
            !response.request.responseURL.includes('user/get/login') &&
            !response.request.responseURL.includes('user/login')
          ){
            message.success("请先登录")
            window.location.href = `/user/login?redirect=${window.location.href}`
          }
        }*/
    //console.log('进入网页响应的数据:' + response.data.code)
    return response;
  },
  function(error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error)
  }
)

export default instance
