<template>
  <div class="login-body" v-loading="loading">
    <el-form
      class="login-form"
      :model="account"
      status-icon
      :rules="rules"
      ref="account"
    >
      <h2 class="title">账号注册</h2>
      <el-form-item prop="userCode">
        <el-input
          v-model="account.userCode"
          minlength="7"
          maxlength="30"
          placeholder="账号"
          size="large"
        >
        </el-input>
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
        <el-button type="primary" @click="submitForm(account)">注册</el-button>
      </el-form-item>
      <p>
        已有账号？<router-link active-class="active" to="/login"
          >去登录</router-link
        >
      </p>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    // 密码规则
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
      account: {
        password: "",
        checkPass: "",
        username: "",
        userCode: "",
      },
      rules: {
        username: [{ required: true, message: "请输入昵称", trigger: "blur" }],
        userCode: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            type: "email",
            message: "账号格式为邮箱",
            trigger: ["blur", "change"],
          },
        ],
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
    // 用户注册
    submitForm(form) {
      this.$refs["account"].validate((valid) => {
        if (valid) {
          axios.post("/api/register", form).then((res) => {
            if (res.data.result === "true") {
              this.$message.success(res.data.msg);
              this.$router.replace("Login");
            } else {
              this.$message.error(res.data.msg);
            }
          });
        } else {
          this.$message.error("信息填写错误！");
          return false;
        }
      });
    },
  },
};
</script>
