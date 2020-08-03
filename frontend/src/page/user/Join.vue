<template>
    <div class="user" id="join"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>회원가입</h1>
                <div class="form-wrap">
                    <div class="input-wrap">
                        <input 
                            v-model="nickName"
                            id="nickName"
                            ref="nickName"
                            placeholder="닉네임을 입력해주세요" 
                            type="text"
                        />
                    </div>

                    <div class="input-wrap">
                        <input 
                            v-model="email" 
                            id="email"
                            ref="email"
                            placeholder="이메일을 입력해주세요"
                            type="email"
                            v-on:keyup="verifyEmail"
                        />
                        <span :class="{active : passwordConfirmType==='text'}">
                                <i id="email_compare" ref="email_compare" class="fas fa-eye">불일치</i> 
                            </span>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input 
                            v-model="password"
                            id="password"
                            ref="password" 
                            :type="passwordType"
                            placeholder="비밀번호를 입력해주세요"
                            v-on:keyup="fn_compare_pwd"
                        />
                        <span :class="{active : passwordConfirmType==='text'}">
                                <i id="pwd_compare" ref="pwd_compare" class="fas fa-eye">불일치</i> 
                            </span>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input 
                            v-model="passwordConfirm" 
                            id="passwordConfirm"
                            ref="passwordConfirm"
                            :type="passwordConfirmType"
                            placeholder="비밀번호를 한번 더 입력해주세요"
                            v-on:keyup="fn_compare_pwd"
                        />
                        <span :class="{active : passwordConfirmType==='text'}">
                            <i class="fas fa-eye"></i> 
                        </span>
                    </div>
                </div>

                <label>
                    <input v-model="isTerm" type="checkbox" id="term"/>
                    <span>약관에 동의합니다</span>
                </label>

                <span class="go-term">약관 보기</span>

            
                <button class="btn" @click="checkHandler">
                    <span>
                        작성완료
                    </span>
                </button>
            </div>


        </div> 
        

    </div>
</template>

<script>
    import '../../assets/css/user.scss'
    import axios from 'axios';
    
    export default {
        name: 'Create',
        components: {
        },
        created(){
            
        },
        data: () => {
            return {
                email: '',
                nickName: '',
                uid: '',
                password: '',
                passwordConfirm: '',
                isTerm: false,
                passwordType:"password",
                passwordConfirmType:"password",
            }
        },
        methods: {
            checkHandler() {
                // alert('123');
                let err = true;
                let msg = '';
                err && !this.nickName && ((msg = '닉네임을 입력해주세요.'), (err = false), this.$refs.nickName.focus());
                err && !this.email && ((msg = '이메일을 입력해주세요.'), (err = false), this.$refs.email.focus());
                err && (document.getElementById('email_compare').innerHTML =='불일치') && ((msg = '이메일을 확인해주세요.'), (err = false), this.$refs.email.focus());
                err && !this.password && ((msg = '비밀번호를 입력해주세요.'), (err = false), this.$refs.password.focus());
                err && !this.passwordConfirm && ((msg = '비밀번호확인을 입력해주세요.'), (err = false), this.$refs.passwordConfirm.focus());
                // err && (this.password != this.passwordConfirm) && ((msg = '비밀번호를 다시 확인해주세요'), (err = false), this.$refs.password.focus());
                err && !(document.getElementById('pwd_compare').innerHTML =='일치') && ((msg = '비밀번호를 다시 확인해주세요.'), (err = false), this.$refs.password.focus());

                err && !this.isTerm && ((msg = '약관에 동의해주세요.'), (err = false));



                if(!err) alert(msg);
                else this.createHandler();
            },
            createHandler() {
                // alert('123');
                // this.moveList();
                axios
                    .post('http://localhost:8080/account/signup', {
                        email: this.email,
                        nickname: this.nickName,
                        password: this.password,
                    })
                    .then((response) => {
                        console.log(response);
                        if(response.data.data === 'success') {
                            alert('등록이 완료되었습니다.');
                            this.moveList();
                        } else {
                            if(response.data.data ==='email'){
                                alert('email이 중복입니다.');
                            }else if(response.data.data ==='nickname'){
                                alert('nickname이 중복입니다.');
                            }
                        }
                        
                    })
                    .catch((error) => {
                        this.error = error;
                        console.log(error);
                        // this.moveList();
                    })
                    .finally(() => {});
            },
            moveList() {
                // alert('123');
                this.$router.push('../');
            },
            fn_compare_pwd() {
                // alert('123');
                var pwd1 = this.password;
                var pwd2 = this.passwordConfirm;
                var regExpPwd = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/i;
                var result = document.getElementById('pwd_compare');
                
                if (pwd1.match(regExpPwd) != null) {
                    if(pwd1 == pwd2) {
                        result.innerText = '일치';
                    } else {
                        result.innerText = '불일치';      
                    }
                } else {
                    result.innerText = '영어/한글 포함 8자 이상';
                }

            },
            verifyEmail() {
                var emailVal = this.email;
                var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
                var result = document.getElementById('email_compare');
                if (emailVal.match(regExp) != null) {
                    result.innerText = '일치';
                } else {
                    result.innerText = '불일치';
                }
            },
        },
        watch: {
        },
    }

</script>


