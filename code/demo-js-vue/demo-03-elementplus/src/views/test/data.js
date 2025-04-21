export default {
  formItem: [
    {
      type: 'input',
      field: 'name',
      label: '姓名',
      placeholder: '请输入姓名'
    },
    {
      type: 'input',
      label: '密码',
      field: 'password',
      placeholder: '请输入密码'
    },
    {
      type: 'switch',
      field: 'checked',
      label: '是否同意'
    },
    {
      type: 'input',
      label: '手机号',
      field: 'phone',
      placeholder: '请输入手机号'
    },
    {
      type: 'select',
      label: '请输入颜色',
      field: 'color',
      placeholder: '请选择颜色',
      options: [
        {
          label: '红色',
          value: 0
        },
        {
          label: '绿色',
          value: 1
        },
      ]
    }
  ]
}