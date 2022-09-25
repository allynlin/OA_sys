"use strict";(self.webpackChunkdemo_ts=self.webpackChunkdemo_ts||[]).push([[691],{4691:function(e,t,n){n.r(t);var r=n(9439),a=n(2791),l=n(586),o=n(1408),s=n(3695),c=n(8389),i=n(3231),u=n(177),d=n(5581),h=n(3707),m=(n(1616),n(8329)),f=n(7689),p=n(1104),Z=n(691),x=n.n(Z),b=(n(335),n(9434)),v=n(7707),C=n(448),g=n(184),y=l.Z.Header,w=l.Z.Footer,j=l.Z.Content,P=function(){var e=(0,b.I0)(),t=o.Z.useForm(),n=(0,r.Z)(t,1)[0],l=o.Z.useWatch("username",n),Z=o.Z.useWatch("password",n),y=o.Z.useWatch("enterPassword",n),w=o.Z.useWatch("realeName",n),j=o.Z.useWatch("gender",n),P=o.Z.useWatch("tel",n),k=o.Z.useWatch("email",n),O=o.Z.useWatch("departmentUid",n),E=o.Z.useWatch("leaderUid",n),I=o.Z.useWatch("rememberme",n),S=(0,a.useState)([]),N=(0,r.Z)(S,2),_=N[0],A=N[1],W=(0,a.useState)([]),T=(0,r.Z)(W,2),q=T[0],F=T[1],L=(0,a.useState)("teacher"),z=(0,r.Z)(L,2),B=z[0],M=z[1],R=(0,a.useState)(!0),$=(0,r.Z)(R,2),H=$[0],U=$[1],V=(0,f.s0)(),D=function(){(0,p.Y9)(l,Z,w,j,P,k,O).then((function(e){s.ZP.success(e.msg),Q(B)}))},G=function(){(0,p.rv)(l,Z,w,j,P,k,E).then((function(e){s.ZP.success(e.msg),Q(B)}))},K=function(){(0,p.QA)(l,Z,w,j,P,k).then((function(e){s.ZP.success(e.msg),Q(B)}))},Q=function(t){if(I)switch(t){case"teacher":(0,p.tz)(l,Z).then((function(t){200===t.code?(e((0,v.ex)()),s.ZP.success(t.msg),Y("teacher"),J(),V("/home/teacher")):(s.ZP.error(t.msg),V("/login")),x().done(!0)}));break;case"department":(0,p.CS)(l,Z).then((function(t){200===t.code?(e((0,v.Af)()),s.ZP.success(t.msg),Y("department"),J(),V("/home/department")):(s.ZP.error(t.msg),V("/login")),x().done(!0)}));break;case"leader":(0,p.d2)(l,Z).then((function(t){200===t.code?(e((0,v.n$)()),s.ZP.success(t.msg),Y("leader"),J(),V("/home/leader")):(s.ZP.error(t.msg),V("/login")),x().done(!0)}));break;default:s.ZP.error("\u7cfb\u7edf\u9519\u8bef\uff0c\u8bf7\u9009\u62e9\u6b63\u786e\u7684\u6ce8\u518c\u7c7b\u578b")}else V("/login");x().done(!0)},Y=function(t){e((0,C.x4)()),m.Z.set("userType",t,{expires:7,path:"/",sameSite:"strict"})},J=function(){m.Z.set("username",l,{expires:7,path:"/",sameSite:"strict"}),m.Z.set("password",Z,{expires:7,path:"/",sameSite:"strict"})},X=function(){x().inc(),(0,p.qI)().then((function(e){x().done(!0);var t=e.body.map((function(e){return{value:e.uid,label:e.realeName}}));A(t)}))},ee=function(){x().inc(),(0,p.UH)().then((function(e){x().done(!0);var t=e.body.map((function(e){return{value:e.uid,label:e.realeName}}));F(t)}))};return(0,g.jsxs)(o.Z,{form:n,name:"login",labelCol:{span:8},wrapperCol:{span:8},onFinish:function(){if(H)if(Z===y)switch(x().inc(),B){case"teacher":D();break;case"department":G();break;case"leader":K();break;default:s.ZP.error("\u7cfb\u7edf\u9519\u8bef\uff0c\u8bf7\u9009\u62e9\u6b63\u786e\u7684\u6ce8\u518c\u7c7b\u578b")}else s.ZP.error("\u4e24\u6b21\u5bc6\u7801\u8f93\u5165\u4e0d\u4e00\u81f4");else s.ZP.error("\u7528\u6237\u540d\u5df2\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165")},initialValues:{gender:"\u7537",rememberme:!0},children:[(0,g.jsx)(o.Z.Item,{wrapperCol:{},children:(0,g.jsxs)(i.ZP.Group,{defaultValue:"teacher",buttonStyle:"solid",onChange:function(e){M(e.target.value)},children:[(0,g.jsx)(i.ZP.Button,{value:"leader",children:"\u9886\u5bfc\u6ce8\u518c"}),(0,g.jsx)(i.ZP.Button,{value:"department",children:"\u90e8\u95e8\u6ce8\u518c"}),(0,g.jsx)(i.ZP.Button,{value:"teacher",children:"\u6559\u5e08\u6ce8\u518c"})]})}),(0,g.jsx)(o.Z.Item,{label:"\u7528\u6237\u540d",name:"username",rules:[{required:!0,message:"\u8bf7\u586b\u5199\u7528\u6237\u540d\uff08\u4e0d\u8d85\u8fc720\u5b57\u7b26\uff09",pattern:/^[a-zA-Z0-9]{1,20}$/}],children:(0,g.jsx)(u.Z,{showCount:!0,maxLength:20,allowClear:!0,onChange:function(e){!function(e){switch(B){case"teacher":(0,p.Su)(e.target.value).then((function(e){200!==e.code?(U(!1),s.ZP.error(e.msg)):U(!0)}));break;case"department":(0,p.rA)(e.target.value).then((function(e){200!==e.code?(U(!1),s.ZP.error(e.msg)):U(!0)}));break;case"leader":(0,p.lg)(e.target.value).then((function(e){200!==e.code?(U(!1),s.ZP.error(e.msg)):U(!0)}));break;default:s.ZP.error("\u7cfb\u7edf\u9519\u8bef\uff0c\u8bf7\u9009\u62e9\u6b63\u786e\u7684\u6ce8\u518c\u7c7b\u578b")}}(e)}})}),(0,g.jsx)(o.Z.Item,{label:"\u5bc6\u7801",name:"password",rules:[{required:!0,message:"\u5bc6\u7801\u5fc5\u987b\u4e3a8-20\u4f4d\u5b57\u6bcd\u6216\u6570\u5b57",pattern:/^[a-zA-Z0-9]{8,20}$/}],children:(0,g.jsx)(u.Z.Password,{showCount:!0,maxLength:20,allowClear:!0})}),(0,g.jsx)(o.Z.Item,{label:"\u786e\u8ba4\u5bc6\u7801",name:"enterPassword",rules:[{required:!0,message:"\u5bc6\u7801\u5fc5\u987b\u4e3a8-20\u4f4d\u5b57\u6bcd\u6216\u6570\u5b57",pattern:/^[a-zA-Z0-9]{8,20}$/}],children:(0,g.jsx)(u.Z.Password,{showCount:!0,maxLength:20,allowClear:!0})}),(0,g.jsx)(o.Z.Item,{label:"\u771f\u5b9e\u59d3\u540d",name:"realeName",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u60a8\u7684\u771f\u5b9e\u59d3\u540d",pattern:/^[\u4e00-\u9fa5]{2,4}$/}],children:(0,g.jsx)(u.Z,{showCount:!0,maxLength:4,allowClear:!0})}),(0,g.jsx)(o.Z.Item,{label:"\u6027\u522b",name:"gender",rules:[{required:!0,message:"\u8bf7\u9009\u62e9\u60a8\u7684\u6027\u522b"}],children:(0,g.jsxs)(i.ZP.Group,{buttonStyle:"solid",style:{display:"flex"},children:[(0,g.jsx)(i.ZP.Button,{value:"\u7537",children:"\u7537"}),(0,g.jsx)(i.ZP.Button,{value:"\u5973",children:"\u5973"})]})}),(0,g.jsx)(o.Z.Item,{label:"\u8054\u7cfb\u7535\u8bdd",name:"tel",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u60a8\u7684\u8054\u7cfb\u7535\u8bdd",pattern:/^1[3456789]\d{9}$/}],children:(0,g.jsx)(u.Z,{type:"tel",showCount:!0,maxLength:11,allowClear:!0})}),(0,g.jsx)(o.Z.Item,{label:"\u7535\u5b50\u90ae\u4ef6",name:"email",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u60a8\u7684\u7535\u5b50\u90ae\u4ef6\u5730\u5740",pattern:/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/}],children:(0,g.jsx)(u.Z,{type:"email",showCount:!0,maxLength:30,allowClear:!0})}),"teacher"===B?(0,g.jsx)(o.Z.Item,{label:"\u90e8\u95e8",name:"departmentUid",rules:[{required:!0,message:"\u8bf7\u9009\u62e9\u60a8\u7684\u4e0a\u7ea7\u90e8\u95e8"}],children:(0,g.jsx)(c.Z,{showSearch:!0,style:{width:"100%"},onFocus:X,placeholder:"\u8bf7\u9009\u62e9\u4f60\u7684\u4e0a\u7ea7\u90e8\u95e8",options:_})}):"department"===B?(0,g.jsx)(o.Z.Item,{label:"\u9886\u5bfc",name:"leaderUid",rules:[{required:!0,message:"\u8bf7\u9009\u62e9\u60a8\u7684\u4e0a\u7ea7\u9886\u5bfc"}],children:(0,g.jsx)(c.Z,{showSearch:!0,style:{width:"100%"},placeholder:"\u8bf7\u9009\u62e9\u60a8\u7684\u4e0a\u7ea7\u9886\u5bfc",onFocus:ee,options:q})}):null,(0,g.jsx)(o.Z.Item,{label:"\u662f\u5426\u81ea\u52a8\u767b\u5f55",name:"rememberme",valuePropName:"checked",rules:[{required:!0,message:"\u8bf7\u9009\u62e9\u662f\u5426\u81ea\u52a8\u767b\u5f55"}],children:(0,g.jsx)(d.Z,{style:{display:"flex"},checkedChildren:"\u662f",unCheckedChildren:"\u5426"})}),(0,g.jsxs)(o.Z.Item,{wrapperCol:{},children:[(0,g.jsx)(h.Z,{type:"primary",htmlType:"submit",children:"\u6ce8\u518c"}),"\xa0\xa0\xa0\xa0",(0,g.jsx)(h.Z,{htmlType:"button",onClick:function(){n.resetFields()},children:"\u91cd\u7f6e"}),"\xa0\xa0\xa0\xa0",(0,g.jsx)(h.Z,{htmlType:"button",onClick:function(){V("/login")},children:"\u767b\u5f55"})]})]})};t.default=function(){return(0,g.jsxs)(l.Z,{children:[(0,g.jsx)(y,{children:(0,g.jsx)("span",{children:"\u6821\u56ed OA \u7cfb\u7edf\u6ce8\u518c"})}),(0,g.jsx)(j,{children:(0,g.jsx)(P,{})}),(0,g.jsx)(w,{children:"\u6821\u56ed OA \u7cfb\u7edf \xa9 2022 Created By allynlin"})]})}},586:function(e,t,n){var r=n(3613),a=n(9779),l=r.ZP;l.Header=r.h4,l.Footer=r.$_,l.Content=r.VY,l.Sider=a.Z,t.Z=l},3231:function(e,t,n){n.d(t,{ZP:function(){return S}});var r=n(7462),a=n(4942),l=n(9439),o=n(1694),s=n.n(o),c=n(5179),i=n(2791),u=n(1929),d=n(1815);var h=i.createContext(null),m=h.Provider,f=h,p=i.createContext(null),Z=p.Provider,x=n(8083),b=n(8834),v=n(9125),C=n(1940),g=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"===typeof Object.getOwnPropertySymbols){var a=0;for(r=Object.getOwnPropertySymbols(e);a<r.length;a++)t.indexOf(r[a])<0&&Object.prototype.propertyIsEnumerable.call(e,r[a])&&(n[r[a]]=e[r[a]])}return n},y=function(e,t){var n,l=i.useContext(f),o=i.useContext(p),c=i.useContext(u.E_),d=c.getPrefixCls,h=c.direction,m=i.useRef(),Z=(0,b.sQ)(t,m),y=(0,i.useContext)(C.aM).isFormItemInput,w=e.prefixCls,j=e.className,P=e.children,k=e.style,O=e.disabled,E=g(e,["prefixCls","className","children","style","disabled"]),I=d("radio",w),S="button"===((null===l||void 0===l?void 0:l.optionType)||o)?"".concat(I,"-button"):I,N=(0,r.Z)({},E),_=i.useContext(v.Z);N.disabled=O||_,l&&(N.name=l.name,N.onChange=function(t){var n,r;null===(n=e.onChange)||void 0===n||n.call(e,t),null===(r=null===l||void 0===l?void 0:l.onChange)||void 0===r||r.call(l,t)},N.checked=e.value===l.value,N.disabled=N.disabled||l.disabled);var A=s()("".concat(S,"-wrapper"),(n={},(0,a.Z)(n,"".concat(S,"-wrapper-checked"),N.checked),(0,a.Z)(n,"".concat(S,"-wrapper-disabled"),N.disabled),(0,a.Z)(n,"".concat(S,"-wrapper-rtl"),"rtl"===h),(0,a.Z)(n,"".concat(S,"-wrapper-in-form-item"),y),n),j);return i.createElement("label",{className:A,style:k,onMouseEnter:e.onMouseEnter,onMouseLeave:e.onMouseLeave},i.createElement(x.Z,(0,r.Z)({},N,{type:"radio",prefixCls:S,ref:Z})),void 0!==P?i.createElement("span",null,P):null)};var w=i.forwardRef(y),j=i.forwardRef((function(e,t){var n,o=i.useContext(u.E_),h=o.getPrefixCls,f=o.direction,p=i.useContext(d.Z),Z=(0,c.Z)(e.defaultValue,{value:e.value}),x=(0,l.Z)(Z,2),b=x[0],v=x[1],C=e.prefixCls,g=e.className,y=void 0===g?"":g,j=e.options,P=e.buttonStyle,k=void 0===P?"outline":P,O=e.disabled,E=e.children,I=e.size,S=e.style,N=e.id,_=e.onMouseEnter,A=e.onMouseLeave,W=e.onFocus,T=e.onBlur,q=h("radio",C),F="".concat(q,"-group"),L=E;j&&j.length>0&&(L=j.map((function(e){return"string"===typeof e||"number"===typeof e?i.createElement(w,{key:e.toString(),prefixCls:q,disabled:O,value:e,checked:b===e},e):i.createElement(w,{key:"radio-group-value-options-".concat(e.value),prefixCls:q,disabled:e.disabled||O,value:e.value,checked:b===e.value,style:e.style},e.label)})));var z=I||p,B=s()(F,"".concat(F,"-").concat(k),(n={},(0,a.Z)(n,"".concat(F,"-").concat(z),z),(0,a.Z)(n,"".concat(F,"-rtl"),"rtl"===f),n),y);return i.createElement("div",(0,r.Z)({},function(e){return Object.keys(e).reduce((function(t,n){return!n.startsWith("data-")&&!n.startsWith("aria-")&&"role"!==n||n.startsWith("data-__")||(t[n]=e[n]),t}),{})}(e),{className:B,style:S,onMouseEnter:_,onMouseLeave:A,onFocus:W,onBlur:T,id:N,ref:t}),i.createElement(m,{value:{onChange:function(t){var n=b,r=t.target.value;"value"in e||v(r);var a=e.onChange;a&&r!==n&&a(t)},value:b,disabled:e.disabled,name:e.name,optionType:e.optionType}},L))})),P=i.memo(j),k=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"===typeof Object.getOwnPropertySymbols){var a=0;for(r=Object.getOwnPropertySymbols(e);a<r.length;a++)t.indexOf(r[a])<0&&Object.prototype.propertyIsEnumerable.call(e,r[a])&&(n[r[a]]=e[r[a]])}return n},O=function(e,t){var n=i.useContext(u.E_).getPrefixCls,a=e.prefixCls,l=k(e,["prefixCls"]),o=n("radio",a);return i.createElement(Z,{value:"button"},i.createElement(w,(0,r.Z)({prefixCls:o},l,{type:"radio",ref:t})))},E=i.forwardRef(O),I=w;I.Button=E,I.Group=P,I.__ANT_RADIO=!0;var S=I},5581:function(e,t,n){n.d(t,{Z:function(){return g}});var r=n(7462),a=n(4942),l=n(7106),o=n(1694),s=n.n(o),c=n(9439),i=n(4925),u=n(2791),d=n(5179),h=n(1354),m=u.forwardRef((function(e,t){var n,r=e.prefixCls,l=void 0===r?"rc-switch":r,o=e.className,m=e.checked,f=e.defaultChecked,p=e.disabled,Z=e.loadingIcon,x=e.checkedChildren,b=e.unCheckedChildren,v=e.onClick,C=e.onChange,g=e.onKeyDown,y=(0,i.Z)(e,["prefixCls","className","checked","defaultChecked","disabled","loadingIcon","checkedChildren","unCheckedChildren","onClick","onChange","onKeyDown"]),w=(0,d.Z)(!1,{value:m,defaultValue:f}),j=(0,c.Z)(w,2),P=j[0],k=j[1];function O(e,t){var n=P;return p||(k(n=e),null===C||void 0===C||C(n,t)),n}var E=s()(l,o,(n={},(0,a.Z)(n,"".concat(l,"-checked"),P),(0,a.Z)(n,"".concat(l,"-disabled"),p),n));return u.createElement("button",Object.assign({},y,{type:"button",role:"switch","aria-checked":P,disabled:p,className:E,ref:t,onKeyDown:function(e){e.which===h.Z.LEFT?O(!1,e):e.which===h.Z.RIGHT&&O(!0,e),null===g||void 0===g||g(e)},onClick:function(e){var t=O(!P,e);null===v||void 0===v||v(t,e)}}),Z,u.createElement("span",{className:"".concat(l,"-inner")},P?x:b))}));m.displayName="Switch";var f=m,p=n(1929),Z=n(9125),x=n(1815),b=n(2833),v=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"===typeof Object.getOwnPropertySymbols){var a=0;for(r=Object.getOwnPropertySymbols(e);a<r.length;a++)t.indexOf(r[a])<0&&Object.prototype.propertyIsEnumerable.call(e,r[a])&&(n[r[a]]=e[r[a]])}return n},C=u.forwardRef((function(e,t){var n,o=e.prefixCls,c=e.size,i=e.disabled,d=e.loading,h=e.className,m=void 0===h?"":h,C=v(e,["prefixCls","size","disabled","loading","className"]),g=u.useContext(p.E_),y=g.getPrefixCls,w=g.direction,j=u.useContext(x.Z),P=u.useContext(Z.Z),k=i||P||d,O=y("switch",o),E=u.createElement("div",{className:"".concat(O,"-handle")},d&&u.createElement(l.Z,{className:"".concat(O,"-loading-icon")})),I=s()((n={},(0,a.Z)(n,"".concat(O,"-small"),"small"===(c||j)),(0,a.Z)(n,"".concat(O,"-loading"),d),(0,a.Z)(n,"".concat(O,"-rtl"),"rtl"===w),n),m);return u.createElement(b.Z,{insertExtraNode:!0},u.createElement(f,(0,r.Z)({},C,{prefixCls:O,className:I,disabled:k,ref:t,loadingIcon:E})))}));C.__ANT_SWITCH=!0;var g=C},335:function(){},1616:function(){}}]);
//# sourceMappingURL=691.32c976a6.chunk.js.map