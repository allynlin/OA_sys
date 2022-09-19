"use strict";(self.webpackChunkos_sys=self.webpackChunkos_sys||[]).push([[325],{1168:function(e,s,n){n.r(s);var r=n(9439),t=(n(2791),n(586)),a=n(1408),l=n(3695),c=n(177),o=n(6592),i=n(5581),m=n(3707),u=(n(1616),n(8329)),h=n(1424),d=n(6871),Z=n(9434),p=n(9124),x=n(9649),f=n(691),j=n.n(f),g=(n(335),n(184)),w=t.Z.Header,C=t.Z.Footer,b=t.Z.Content,y=function(){var e=(0,Z.I0)(),s=a.Z.useForm(),n=(0,r.Z)(s,1)[0],t=a.Z.useWatch("username",n),f=a.Z.useWatch("password",n),w=a.Z.useWatch("channel",n),C=a.Z.useWatch("rememberme",n),b=(0,d.s0)(),y=function(){(0,h.tz)(t,f).then((function(s){j().done(!0),console.log(s),200===s.code?(e((0,x.ex)()),l.ZP.success(s.msg),k("teacher"),I(),b("/home/teacher")):(l.ZP.error(s.msg),S())}))},v=function(){(0,h.CS)(t,f).then((function(s){j().done(!0),console.log(s),200===s.code?(e((0,x.Af)()),l.ZP.success(s.msg),k("department"),I(),b("/home/department")):(l.ZP.error(s.msg),S())}))},P=function(){(0,h.d2)(t,f).then((function(s){j().done(!0),console.log(s),200===s.code?(e((0,x.n$)()),l.ZP.success(s.msg),k("leader"),I(),b("/home/teacher")):(l.ZP.error(s.msg),S())}))},k=function(s){e((0,p.x4)()),u.Z.set("userType",s,{expires:7,path:"/",sameSite:"strict"})},I=function(){C?(u.Z.set("username",t,{expires:7,path:"/",sameSite:"strict"}),u.Z.set("password",f,{expires:7,path:"/",sameSite:"strict"}),u.Z.set("channel",w,{expires:7,path:"/",sameSite:"strict"})):(u.Z.remove("username"),u.Z.remove("password"),u.Z.remove("channel"))},S=function(){u.Z.remove("username"),u.Z.remove("password"),u.Z.remove("channel"),u.Z.remove("userType")};return(0,g.jsxs)(a.Z,{form:n,name:"login",labelCol:{span:8},wrapperCol:{span:8},onFinish:function(){switch(j().inc(),w){case"teacher":y();break;case"department":v();break;case"leader":P()}},initialValues:{username:u.Z.get("username")||"",password:u.Z.get("password")||"",rememberme:!0,channel:u.Z.get("channel")||"teacher"},children:[(0,g.jsx)(a.Z.Item,{label:"\u7528\u6237\u540d",name:"username",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u7528\u6237\u540d",pattern:/^[a-zA-Z0-9]{1,20}$/}],children:(0,g.jsx)(c.Z,{showCount:!0,maxLength:20,allowClear:!0})}),(0,g.jsx)(a.Z.Item,{label:"\u5bc6\u7801",name:"password",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u5bc6\u7801",pattern:/^[a-zA-Z0-9]{8,20}$/}],children:(0,g.jsx)(c.Z.Password,{showCount:!0,maxLength:20,allowClear:!0})}),(0,g.jsx)(a.Z.Item,{label:"\u767b\u5f55\u6e20\u9053",name:"channel",rules:[{required:!0,message:"\u5fc5\u987b\u9009\u62e9\u767b\u9646\u6e20\u9053"}],children:(0,g.jsxs)(o.ZP.Group,{style:{display:"flex"},buttonStyle:"solid",children:[(0,g.jsx)(o.ZP.Button,{value:"teacher",children:"\u6559\u5e08"}),(0,g.jsx)(o.ZP.Button,{value:"department",children:"\u90e8\u95e8"}),(0,g.jsx)(o.ZP.Button,{value:"leader",children:"\u9886\u5bfc"})]})}),(0,g.jsx)(a.Z.Item,{label:"\u8bb0\u4f4f\u5bc6\u7801",name:"rememberme",valuePropName:"checked",rules:[{required:!0,message:"\u5fc5\u987b\u9009\u62e9\u662f\u5426\u8bb0\u4f4f\u5bc6\u7801"}],children:(0,g.jsx)(i.Z,{style:{display:"flex"},checkedChildren:"\u662f",unCheckedChildren:"\u5426"})}),(0,g.jsxs)(a.Z.Item,{wrapperCol:{},children:[(0,g.jsx)(m.Z,{type:"primary",htmlType:"submit",children:"\u767b\u5f55"}),"\xa0\xa0\xa0\xa0",(0,g.jsx)(m.Z,{htmlType:"button",onClick:function(){n.resetFields()},children:"\u91cd\u7f6e"}),"\xa0\xa0\xa0\xa0",(0,g.jsx)(m.Z,{htmlType:"button",onClick:function(){b("/register")},children:"\u6ce8\u518c"})]})]})};s.default=function(){return(0,g.jsxs)(t.Z,{children:[(0,g.jsx)(w,{children:(0,g.jsx)("span",{children:"\u6821\u56ed OA \u7cfb\u7edf\u767b\u9646"})}),(0,g.jsx)(b,{children:(0,g.jsx)(y,{})}),(0,g.jsx)(C,{children:"\u6821\u56ed OA \u7cfb\u7edf \xa9 2022 Created By allynlin"})]})}},1616:function(){}}]);
//# sourceMappingURL=325.3a5c1c7e.chunk.js.map