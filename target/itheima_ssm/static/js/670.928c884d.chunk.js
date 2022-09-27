"use strict";(self.webpackChunkdemo_ts=self.webpackChunkdemo_ts||[]).push([[670],{2500:function(e,s,n){n.r(s);var r=n(9439),t=n(2791),a=n(586),c=n(7947),l=n(3695),o=n(9389),i=n(6592),m=n(5581),u=n(7309),h=(n(1616),n(8329)),d=n(607),Z=n(7689),p=n(9434),x=n(448),f=n(7707),j=n(691),w=n.n(j),g=(n(335),n(184)),C=a.Z.Header,b=a.Z.Footer,v=a.Z.Content,y=function(){var e=(0,p.I0)(),s=c.Z.useForm(),n=(0,r.Z)(s,1)[0],a=c.Z.useWatch("username",n),j=c.Z.useWatch("password",n),C=c.Z.useWatch("channel",n),b=c.Z.useWatch("rememberme",n),v=(0,Z.s0)();(0,t.useEffect)((function(){-1!==window.location.hash.indexOf("403")&&l.ZP.error("\u8eab\u4efd\u6821\u9a8c\u5931\u8d25\uff0c\u8bf7\u91cd\u65b0\u767b\u5f55")}),[]);var y=function(){(0,d.tz)(a,j).then((function(s){w().done(!0),200===s.code?(e((0,f.ex)()),l.ZP.success(s.msg),I("teacher"),S(),v("/home/teacher")):(l.ZP.error(s.msg),A())}))},P=function(){(0,d.CS)(a,j).then((function(s){w().done(!0),200===s.code?(e((0,f.Af)()),l.ZP.success(s.msg),I("department"),S(),v("/home/department")):(l.ZP.error(s.msg),A())}))},k=function(){(0,d.d2)(a,j).then((function(s){w().done(!0),200===s.code?(e((0,f.n$)()),l.ZP.success(s.msg),I("leader"),S(),v("/home/teacher")):(l.ZP.error(s.msg),A())}))},I=function(s){e((0,x.x4)()),h.Z.set("userType",s,{expires:7,path:"/",sameSite:"strict"})},S=function(){b?(h.Z.set("username",a,{expires:7,path:"/",sameSite:"strict"}),h.Z.set("password",j,{expires:7,path:"/",sameSite:"strict"}),h.Z.set("channel",C,{expires:7,path:"/",sameSite:"strict"})):(h.Z.remove("username"),h.Z.remove("password"),h.Z.remove("channel"))},A=function(){h.Z.remove("username"),h.Z.remove("password"),h.Z.remove("channel"),h.Z.remove("userType")};return(0,g.jsxs)(c.Z,{form:n,name:"login",labelCol:{span:8},wrapperCol:{span:8},onFinish:function(){switch(w().inc(),C){case"teacher":y();break;case"department":P();break;case"leader":k()}},initialValues:{username:h.Z.get("username")||"",password:h.Z.get("password")||"",rememberme:!0,channel:h.Z.get("channel")||"teacher"},children:[(0,g.jsx)(c.Z.Item,{label:"\u7528\u6237\u540d",name:"username",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u7528\u6237\u540d",pattern:/^[a-zA-Z0-9]{1,20}$/}],children:(0,g.jsx)(o.Z,{showCount:!0,maxLength:20,allowClear:!0})}),(0,g.jsx)(c.Z.Item,{label:"\u5bc6\u7801",name:"password",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u5bc6\u7801",pattern:/^[a-zA-Z0-9]{8,20}$/}],children:(0,g.jsx)(o.Z.Password,{showCount:!0,maxLength:20,allowClear:!0})}),(0,g.jsx)(c.Z.Item,{label:"\u767b\u5f55\u6e20\u9053",name:"channel",rules:[{required:!0,message:"\u5fc5\u987b\u9009\u62e9\u767b\u9646\u6e20\u9053"}],children:(0,g.jsxs)(i.ZP.Group,{style:{display:"flex"},buttonStyle:"solid",children:[(0,g.jsx)(i.ZP.Button,{value:"teacher",children:"\u6559\u5e08"}),(0,g.jsx)(i.ZP.Button,{value:"department",children:"\u90e8\u95e8"}),(0,g.jsx)(i.ZP.Button,{value:"leader",children:"\u9886\u5bfc"})]})}),(0,g.jsx)(c.Z.Item,{label:"\u8bb0\u4f4f\u5bc6\u7801",name:"rememberme",valuePropName:"checked",rules:[{required:!0,message:"\u5fc5\u987b\u9009\u62e9\u662f\u5426\u8bb0\u4f4f\u5bc6\u7801"}],children:(0,g.jsx)(m.Z,{style:{display:"flex"},checkedChildren:"\u662f",unCheckedChildren:"\u5426"})}),(0,g.jsxs)(c.Z.Item,{wrapperCol:{},children:[(0,g.jsx)(u.Z,{type:"primary",htmlType:"submit",children:"\u767b\u5f55"}),"\xa0\xa0\xa0\xa0",(0,g.jsx)(u.Z,{htmlType:"button",onClick:function(){n.resetFields()},children:"\u91cd\u7f6e"}),"\xa0\xa0\xa0\xa0",(0,g.jsx)(u.Z,{htmlType:"button",onClick:function(){v("/register")},children:"\u6ce8\u518c"})]})]})};s.default=function(){return(0,g.jsxs)(a.Z,{children:[(0,g.jsx)(C,{children:(0,g.jsx)("span",{children:"\u6821\u56ed OA \u7cfb\u7edf\u767b\u9646"})}),(0,g.jsx)(v,{children:(0,g.jsx)(y,{})}),(0,g.jsx)(b,{children:"\u6821\u56ed OA \u7cfb\u7edf \xa9 2022 Created By allynlin"})]})}},1616:function(){}}]);
//# sourceMappingURL=670.928c884d.chunk.js.map