## 项目简介
该应用为针对武汉东湖学院开发，教务系统为正方教务系统。

应用带有全局过滤器，需要登录使用，无需注册，账号密码为学校教务处学号密码。

登录时会模拟登录教务，抓取姓名、学院、班级等基本信息。

项目采用了验证码自动识别，用户无需输入验证码即可登录。如果用户勾选保持登录，那么系统会将教务系统cookie保存到数据库，用户下次进行与教务相关的操作时直接使用此cookie，此cookie失效时系统会自动重新登录并且更新cookie。

用户登录成功后系统会下发cookie，其内容为采用对称加密算法加密过的用户信息。当服务端session失效时，如果用户携带此cookie访问，服务端会对cookie进行解密验证，全部信息合法则恢复登录状态。

前端框架采用MUI，服务端框架采用SpringBoot。

## 功能介绍
### 奖学金申请表一键生成（开发中...）
点击功能按钮后，需要用户填写操行评定及奖惩等相关信息，之后系统会自动登录教务拉取对应学年成绩，根据科目数、字段长度等自适应表格，智能计算、填表。

用户可直接保存生成的PDF用于打印。
系统会保存最后一次生成的PDF，之后进入可直接查看、下载。

### 一键评教（待开发...）
用户点击一键评教之后，系统自动提交评教表单。实现真正意义上的一键评教。

### 失物招领 & 寻物启事（待开发...）
首次进入需要填写昵称、联系方式等个人信息。该信息应用内模块间共享。
- 发布遗失/捡到物品的信息，包含图片、物品名、地点等信息。
- 查看发布者之前填写的联系信息。
- 删除、更改自己发布的信息。

### 跳蚤市场（待开发...）
首次进入需要填写昵称、联系方式等个人信息。该信息应用内模块间共享。
- 发布闲置，包含图片、描述、价格等信息
- 查看发布者的联系信息
- 删除、更改自己发布的信息。
不支持线上交易，交易方式仅为校内面交。

### 表白墙（待开发...）
首次进入需要填写昵称、联系方式等个人信息。该信息应用内模块间共享。
- 匿名发布与实名发布
- 关键字查找
- 点赞、评论功能，emoji表情支持。

### 意见与建议（待开发...）
简单的表单，用于接受用户反馈。
