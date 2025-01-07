declare namespace API {
  type BaseResponseBoolean = {
    code?: number
    data?: boolean
    message?: string
  }

  type BaseResponseLoggingUserVO = {
    code?: number
    data?: LoggingUserVO
    message?: string
  }

  type BaseResponseLong = {
    code?: number
    data?: number
    message?: string
  }

  type BaseResponsePageUserVO = {
    code?: number
    data?: PageUserVO
    message?: string
  }

  type BaseResponseUser = {
    code?: number
    data?: User
    message?: string
  }

  type BaseResponseUserVO = {
    code?: number
    data?: UserVO
    message?: string
  }

  type DeleteRequest = {
    id?: number
  }

  type getUserByIdParams = {
    id: number
  }

  type getUserVOByIdParams = {
    id: number
  }

  type LoggingUserVO = {
    id?: number
    userAccount?: string
    username?: string
    avatarUrl?: string
    userProfile?: string
    gender?: number
    phone?: string
    email?: string
    tags?: string[]
    userRole?: string
  }

  type OrderItem = {
    column?: string
    asc?: boolean
  }

  type PageUserVO = {
    records?: UserVO[]
    total?: number
    size?: number
    current?: number
    orders?: OrderItem[]
    optimizeCountSql?: boolean
    searchCount?: boolean
    optimizeJoinOfCountSql?: boolean
    countId?: string
    maxLimit?: number
    pages?: number
  }

  type User = {
    id?: number
    userAccount?: string
    userPassword?: string
    username?: string
    avatarUrl?: string
    userProfile?: string
    gender?: number
    phone?: string
    email?: string
    tags?: string
    userRole?: string
    userStatus?: number
    createTime?: string
    updateTime?: string
    isDelete?: number
  }

  type UserAddRequest = {
    userName?: string
    userAccount?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
  }

  type UserLoginRequest = {
    userAccount?: string
    userPassword?: string
  }

  type UserQueryRequest = {
    current?: number
    pageSize?: number
    sortField?: string
    sortOrder?: string
    id?: number
    userName?: string
    userAccount?: string
    userProfile?: string
    userRole?: string
  }

  type UserRegisterRequest = {
    userAccount?: string
    userPassword?: string
    checkPassword?: string
    email?: string
  }

  type UserUpdateRequest = {
    id?: number
    userPassword?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    phone?: string
    email?: string
    userRole?: string
  }

  type UserVO = {
    id?: number
    userAccount?: string
    userPassword?: string
    username?: string
    avatarUrl?: string
    userProfile?: string
    gender?: number
    phone?: string
    email?: string
    tags?: string[]
    userRole?: string
    userStatus?: number
  }
}
