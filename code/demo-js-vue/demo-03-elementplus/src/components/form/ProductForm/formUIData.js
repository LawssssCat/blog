export const services = [["7天无理由退货", 1], ["30天保价", 2], ["2年只换不修", 3]];

export const formProps = {
  formData: {
    type: Object,
    required: true,
  },
  submitText: {
    type: String,
    default: "提交",
  },
  loading: {
    type: Boolean,
    default: true,
  },
};
