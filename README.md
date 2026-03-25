# 项目配置说明

## ✅ 已正确配置（你的）
- **微信小程序 AppID**: `wx3f3f0e917148b693`
- **微信小程序 Secret**: `0ec30a213e6de9067dd705d8ee2ec89e`
- **微信支付测试模式**: 已开启 (`testMode: true`)

---

## ⚠️ 需要检查/修改的配置

### 1. 数据库配置（必须检查）
**文件位置**: `sky-server/src/main/resources/application-dev.yml`

```yaml
sky:
  datasource:
    database: sky_take_out      # 确保数据库存在
    username: root              # 改为自己的数据库用户名
    password: 123456            # 改为自己的数据库密码
```

**影响**: 
- ❌ 配置错误会导致数据库连接失败，项目无法启动
- ✅ 如果和本地一致则无需修改

---

### 2. Redis 配置（必须检查）
**文件位置**: `sky-server/src/main/resources/application-dev.yml`

```yaml
sky:
  redis:
    host: localhost             # Redis 服务器地址
    port: 6379                  # Redis 端口
    database: 0                 # Redis 数据库编号
```

**影响**:
- ❌ 配置错误会导致缓存功能失效
- ✅ 如果和本地一致则无需修改

---

### 3. 阿里云 OSS 配置（可选）
**文件位置**: `sky-server/src/main/resources/application-dev.yml`

```yaml
sky:
  alioss:
    access-key-id: ${YOUR_ACCESS_KEY_ID}        # TODO: 替换为自己的
    access-key-secret: ${YOUR_ACCESS_KEY_SECRET} # TODO: 替换为自己的
    bucket-name: ${YOUR_BUCKET_NAME}             # TODO: 替换为自己的
```

**影响**:
- ❌ 使用别人的配置可能导致图片上传失败
- ✅ 如果不使用图片上传功能可忽略
- 📝 如何获取：
  1. 登录阿里云官网
  2. 开通 OSS 服务
  3. 创建 Bucket
  4. 获取 AccessKey

---

### 4. JWT 密钥（建议修改）
**文件位置**: `sky-server/src/main/resources/application.yml`

```yaml
sky:
  jwt:
    admin-secret-key: itcast   # TODO: 生产环境建议修改
    user-secret-key: itheima   # TODO: 生产环境建议修改
```

**影响**:
- ⚠️ 这是培训机构的默认密钥，安全性低
- ✅ 学习环境可不改
- 🔒 生产环境必须修改（生成随机字符串）

---

### 5. 店铺地址和百度地图 AK（建议修改）
**文件位置**: `sky-server/src/main/resources/application.yml`

```yaml
sky:
  shop:
    address: 北京市海淀区上地十街 10号  # TODO: 替换为实际店铺地址
  baidu:
    ak: your-ak                        # TODO: 替换为实际的百度地图 AK
```

**影响**:
- ⚠️ 店铺地址是假的，配送范围检查会有问题
- ⚠️ 百度 AK 是占位符，超出 5000 米会报错
- 📝 如何获取百度 AK：
  1. 访问 http://lbsyun.baidu.com/
  2. 注册账号并创建应用
  3. 获取 AK（Access Key）

---

### 6. 微信支付配置（已通过测试模式绕过）
**文件位置**: `sky-server/src/main/resources/application-dev.yml`

```yaml
sky:
  wechat:
    testMode: true  # ✅ 已开启测试模式，不会真实扣款
```

**影响**:
- ✅ 测试模式下不会调用真实微信支付
- ✅ 点击支付直接返回成功，可用于学习测试
- ⚠️ 如果要真实收款，需要申请自己的商户号并配置所有证书

---

## 🎯 总结

### 必须检查的配置（影响启动）：
1. ✅ 数据库配置（database、username、password）
2. ✅ Redis 配置（host、port）

### 建议修改的配置（影响功能）：
1. ⚠️ 阿里云 OSS（影响图片上传）
2. ⚠️ 店铺地址和百度 AK（影响配送范围检查）
3. ⚠️ JWT 密钥（安全性考虑）

### 已解决的配置：
1. ✅ 微信支付（已通过测试模式绕过）
2. ✅ 小程序配置（已经是你的）

---

## 📞 常见问题

### Q1: 数据库连接失败？
A: 检查 MySQL 是否启动，数据库名、用户名、密码是否正确

### Q2: Redis 连接失败？
A: 检查 Redis 服务是否启动，端口是否正确

### Q3: 图片上传失败？
A: 需要配置自己的阿里云 OSS，或者暂时不使用此功能

### Q4: 配送范围检查报错？
A: 需要配置正确的店铺地址和百度地图 AK
