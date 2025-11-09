<template>
  <div class="form-control" :style="containerStyle">
    <input
      v-model="inputValue"
      :type="isPasswordVisible ? 'text' : type"
      :required="required"
      :placeholder="placeholder"
      @focus="handleFocus"
      @blur="handleBlur"
    />
    <label>
      <span
        v-for="(char, index) in labelChars"
        :key="index"
        :style="{ transitionDelay: `${index * delayInterval}ms` }"
      >
        {{ char }}
      </span>
    </label>
    <span
      v-if="type === 'password' && inputValue"
      class="toggle-password"
      @click="togglePasswordVisibility"
    >
      <Icon
        :icon="isPasswordVisible ? 'mdi:eye-off' : 'mdi:eye'"
        width="20"
        height="20"
      />
    </span>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from "vue";
import { Icon } from "@iconify/vue";

// 定义 props
interface Props {
  modelValue?: string;
  label?: string;
  type?: string;
  required?: boolean;
  focusDelay?: number; // 每个字符的延迟间隔（毫秒）
  width?: string;
  height?: string;
  fontSize?: string;
  placeholder?: string;
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: "",
  label: "Label",
  type: "text",
  required: true,
  focusDelay: 50,
  width: "190px",
  height: "auto",
  fontSize: "18px",
  placeholder: "",
});
const isPasswordVisible = ref(false);

// 定义 emits
const emit = defineEmits<{
  "update:modelValue": [value: string];
}>();

// 响应式数据
const inputValue = ref(props.modelValue);
const isFocused = ref(false);

// 计算属性
const labelChars = computed(() => props.label.split(""));
const delayInterval = computed(() => props.focusDelay);
const containerStyle = computed(() => ({
  width: props.width,
  height: props.height,
  margin: "20px 0 40px",
}));

// 方法
const handleFocus = () => {
  isFocused.value = true;
};

const handleBlur = () => {
  isFocused.value = false;
};

const togglePasswordVisibility = () => {
  isPasswordVisible.value = !isPasswordVisible.value;
};

// 监听输入值变化并同步到父组件
watch(inputValue, (newValue) => {
  emit("update:modelValue", newValue);
});

// 监听父组件传入的值变化
watch(
  () => props.modelValue,
  (newValue) => {
    inputValue.value = newValue;
  }
);
</script>

<style scoped>
.form-control {
  position: relative;
  display: inline-block;
}

.form-control input {
  background-color: transparent;
  border: 0;
  border-bottom: 2px #333 solid;
  display: block;
  width: 100%;
  padding: 15px 0 0 0;
  font-size: v-bind("props.fontSize");
  color: #333;
  box-sizing: border-box;
}

.form-control input:focus,
.form-control input:valid {
  outline: 0;
  border-bottom-color: #42b983;
}

.form-control label {
  position: absolute;
  top: 15px;
  left: 0;
  pointer-events: none;
}

.form-control label span {
  display: inline-block;
  font-size: v-bind("props.fontSize");
  min-width: 5px;
  color: #333;
  transition: 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.form-control input:focus + label span,
.form-control input:valid + label span {
  color: #42b983;
  transform: translateY(-30px);
}
.toggle-password {
  position: absolute;
  right: 5px;
  top: calc(100% - 16px);
  transform: translateY(-50%);
  cursor: pointer;
  color: #666;
  z-index: 2;
  pointer-events: auto;
}
</style>
