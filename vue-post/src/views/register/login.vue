<template>
  <div class="login-body" v-loading="loading">
    <el-form
      class="login-form"
      :model="account"
      status-icon
      :rules="rules"
      ref="account"
    >
      <h2 class="title">后台管理</h2>
      <el-form-item prop="userCode">
        <el-input
          v-model="account.userCode"
          minlength="7"
          maxlength="30"
          size="large"
          placeholder="账号"
        >
        </el-input>
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
      <el-form-item>
        <el-button type="primary" @click="submitForm(account)">登录</el-button>
      </el-form-item>
      <p>
        没有账号？<router-link active-class="active" to="/register"
          >去注册</router-link
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
    return {
      loading: false,
      account: {
        password: "1234567",
        userCode: "999@qq.com",
      },
      // 规则设置
      rules: {
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
      },
    };
  },
  methods: {
    // 用户登录
    submitForm(form) {
      this.$refs["account"].validate((valid) => {
        if (valid) {
          this.loading = true;
          axios
            .post("api/login", form)
            .then((res) => {
              let result = JSON.parse(res.data.result)
              if (result.result === "true") {
                // sessionStorage.setItem("user", this.account.userCode);
                this.$message.success(result.msg);
                this.$store.commit("setUser", res.data.user)
                this.$router.push({
                  path:"/",
                });
              } else {
                this.account.password = "";
                this.$message.error(result.msg);
              }
              this.loading = false;
            })
            .catch(() => {
              this.account.password = "";
              this.loading = false;
            });
        } else {
          this.$message.error("信息填写有误！");
          return false;
        }
      });
    },
  },
};
</script>

<style>
.login-body {
  padding: 0 11%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #1f2741;
  height: 100%;
  color: #dddeee;
}
.login-form {
  font-size: 14px;
  width: calc(80% - 50px);
  margin: 0 auto;
  max-width: 270px;
  position: relative;
  box-shadow: 0 12px 15px 0 rgb(0 0 0 / 24%), 0 17px 50px 0 rgb(0 0 0 / 19%);
  padding: calc(7% + 11px) 12%;
  background-color: rgb(33, 41, 66);
  border-radius: 11px;
  border-bottom: 9px solid #79a6fe;
  box-shadow: 1px 1px 108px 19px rgb(25 31 53);
}
.login-form input {
  display: block;
  margin: 7px auto;
  background: #272f4b;
  border: 0;
  border-radius: 7px;
  padding: 15px 11px;
  outline: none;
  color: #d6d6d6;
}
.login-form .el-button {
  width: 100%;
  border: 0;
  border-radius: 50px;
  margin-bottom: 7px;
  margin-top: 3px;
  height: 37px;
}
.title {
  margin-bottom: 33px;
}
</style>