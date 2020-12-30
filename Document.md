# 积分领券APP

## 需求说明

### APP工作流程

```flow
st=>start: APP启动
op1=>operation: 播放广告
检测人脸
等待消费信息
cond0=>condition: 接收到推送？
cond1=>condition: 检测到人脸?
op2=>operation: 停止播放广告
显示优惠券领取提示
cond2=>condition: 点击领券按钮？
op3=>operation: 人脸数据+优惠券类型
请求服务端
cond4=>condition: 新用户？
op4=>operation: 扫码登录领券
op5=>operation: 显示领券结果
wait30=>operation: 30秒超时后返回
op7=>operation: 停止播放广告
显示积分累积提示
cond5=>condition: 点击积分累加
op8=>operation: 人脸数据+优惠券类型
请求服务端
cond6=>condition: 新用户？
op9=>operation: 扫码登录领券
op10=>operation: 显示领券结果
wait31=>operation: 30秒超时后返回
st->op1->cond0
op2->cond2
op3->cond4
op4->op5(right)->wait30->op1
op7->cond5
op8->cond6
op9->op10(right)->wait31(bottom)->op1
cond0(no)->cond1
cond0(yes)->op7
cond1(no)->op1
cond1(yes)->op2
cond2(no)->wait30
cond2(yes)->op3
cond4(yes)->op4
cond4(no)->op5
cond5(no)->wait31
cond5(yes)->op8
cond6(yes)->op9
cond6(no)->op10
```