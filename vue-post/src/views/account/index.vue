<template>
  <div class="account-main" v-loading="loading">
    <!-- <el-avatar shape="circle" :size="100" :src="url"></el-avatar> -->
    <el-tooltip
      class="item"
      effect="dark"
      content="点击上传头像"
      placement="bottom"
    >
      <el-upload
        class="avatar-uploader"
        action="a/user/upload"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
      >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </el-tooltip>
    <el-form
      :model="account"
      status-icon
      :rules="rules"
      ref="account"
      label-width="100px"
    >
      <el-form-item label="账号" prop="userCode">
        <el-link disabled>{{ account.userCode }} </el-link>
      </el-form-item>
      <el-form-item prop="username">
        <el-input
          v-model="account.username"
          minlength="1"
          maxlength="16"
          placeholder="昵称"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="account.password"
          autocomplete="off"
          size="large"
          minlength="5"
          maxlength="16"
          placeholder="密码"
        ></el-input>
      </el-form-item>
      <el-form-item prop="checkPass">
        <el-input
          type="password"
          v-model="account.checkPass"
          autocomplete="off"
          size="large"
          minlength="5"
          maxlength="16"
          placeholder="确认密码"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(account)"
          >更新信息</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error("密码长度不能小于5"));
      } else {
        if (this.account.checkPass !== "") {
          this.$refs.account.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.account.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      loading: false,
      // 头像
      imageUrl: "",
      // 账户信息
      account: {
        userCode: "",
        username: "",
        password: "",
        avatar: "",
        checkPass: "",
      },
      // 表单规则
      rules: {
        username: [{ required: true, message: "请输入昵称", trigger: "blur" }],
        password: [
          { required: true, validator: validatePass, trigger: "blur" },
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    // 更新用户信息
    submitForm(form) {
      this.$refs["account"].validate((valid) => {
        if (valid) {
          axios
            .post("a/user/update", form)
            .then((res) => {
              if (res.data.result === "true") {
                this.$store.commit("setUser", this.account);
                this.$message.success(res.data.msg);
              } else {
                this.$message.error(res.data.msg);
              }
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          this.$message.error("信息填写有误！");
          return false;
        }
      });
    },
    // 头像上传成功
    handleAvatarSuccess(res, file) {
      if (res.result === "true") {
        this.$message.success(res.msg);
      } else {
        this.$message.error(res.msg);
      }
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isLt1M = file.size / 1024 / 1024 < 1;

      if (!isLt1M) {
        this.$message.error("上传头像图片大小不能超过 1MB!");
      }
      return isLt1M;
    },
  },
  created() {
    // 获取用户信息
    this.loading = true;
    axios
      .get("a/user/get")
      .then((res) => {
        res.data.password = "";
        this.account = res.data;
        this.imageUrl = res.data.avatar;
        this.loading = false;
      })
      .catch(() => {
        this.loading = false;
      });
  },
};
</script>

<style>
.account-main {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin: 7% 0 0 -11%;
}
.avatar-uploader .el-upload {
  border: 1px dashed #c0ccda;
  border-radius: 12px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 99px;
  height: 99px;
  line-height: 99px;
  text-align: center;
}
.avatar {
  width: 99px;
  height: 99px;
  display: block;
}
</style>