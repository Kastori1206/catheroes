<template>
    <div class="user" id="login">
        <div class="wrapC table">
            <div class="middle">
                <h2>사용자 정보</h2>
                <div class="input-wrap">
                    <h1>{{userinfo.nickname}}님 안녕하세요</h1>
                    {{userinfo.email}}
                    
                </div>                
            </div>
            
        </div>

    </div>
</template>

<script>
    import '../../assets/css/user.scss'
    import constants from '../../lib/constants'    
    
    export default {
        components: {
        },
        created(){
        },
        watch: {
        },
        methods: {
        },
        data: () => {
            return {
                constants,
                loginData: {
                    email: '',
                    password: '',
                }

            }
        },
        props : ['userinfo']

    }

</script>
