<template>
  <t-card>
    <t-space direction="vertical" style="width: 100%">
      <div>
        <p style="font-size: 18px">上传词曲</p>
      </div>
      <div>
        <p class="tips">
          一次可同时上传多首歌的「音频+歌词文件」，歌词文件需和音频文件名称保持一致（一次上传不超过30首）
          <t-tooltip :content="tooltip" placement="bottom-left" theme="light">
            <a style="color: #1677ff">查看示例</a>
          </t-tooltip>
        </p>
        <div class="button-container">
          <t-button @click="push">批量上传</t-button>
        </div>
      </div>
      <div>
        <t-table
          v-if="tableShow"
          bordered="true"
          row-key="key"
          :data="tableData"
          :columns="columns"
          :expand-on-row-click="false"
          :expand-icon="true"
          @expand-change="handleExpandChange"
        >
          <template #expanded-row="{ row }">
            <t-row :gutter="50">
              <t-col :span="15">
                <div style="display: flex; align-items: center; gap: 8px">
                  <p style="margin: 0; width: 70px; text-align: right">语种：</p>
                  <t-select
                    v-model:value="row.language"
                    :options="options"
                    placeholder="请选择语种"
                    clearable
                  ></t-select>
                </div>
              </t-col>
              <t-col :span="15">
                <div style="display: flex; align-items: center; gap: 8px">
                  <p style="margin: 0; width: 75px; text-align: right">词作者：</p>
                  <t-input v-model:value="row.lyricists" />
                </div>
              </t-col>
              <t-col :span="15">
                <div style="display: flex; align-items: center; gap: 8px">
                  <p style="margin: 0; width: 75px; text-align: right">曲作者：</p>
                  <t-input v-model:value="row.songwriter" />
                </div>
              </t-col>
              <t-col :span="15">
                <div style="display: flex; align-items: center; gap: 8px">
                  <p style="margin: 0; width: 70px; text-align: right">标签：</p>
                  <t-select v-model:value="row.tab" :options="options" placeholder="请选择标签" clearable></t-select>
                </div>
              </t-col>
              <t-col :span="15">
                <div style="display: flex; align-items: center; gap: 8px">
                  <p style="margin: 0; width: 70px; text-align: right">商务：</p>
                  <t-select
                    v-model:value="row.businessCommissioner"
                    :options="options"
                    placeholder="请选择商务"
                    clearable
                  ></t-select>
                </div>
              </t-col>
            </t-row>
          </template>
          <template #operation="{ row }">
            <t-link theme="primary" hover="color" @click="rehandleClickOp(row.index)"> 删除</t-link>
          </template>
        </t-table>
      </div>
    </t-space>
    <div class="upload-div">
      <t-row>
        <t-col :span="24" flex="auto">
          <div class="upload-container">
            <t-upload
              v-model="files"
              style="width: 100%"
              multiple
              :request-method="requestMethod"
              :max="30"
              accept=".mp3,audio/mpeg,.wav,.txt,.doc,.docx"
              :file-list-display="customFileListDisplay"
              :on-change="change"
              draggable
              theme="custom"
              :allow-upload-duplicate-file="true"
            >
              <!--              :drag-content="draggerCenter"-->
              <template #dragContent>
                <div style="display: flex; align-items: center; width: 100%">
                  <p>点击此处上传或将文件拖拽到此区域</p>
                </div>
              </template>
            </t-upload>
          </div>
        </t-col>
      </t-row>
      <br /><br />
      <t-row :gutter="4" justify="center">
        <t-col :span="1">
          <t-button theme="primary" shape="round" variant="base" @click="push">确定上传</t-button>
        </t-col>
        <t-col :span="1">
          <t-button shape="round" variant="outline">取消全部</t-button>
        </t-col>
      </t-row>
    </div>
  </t-card>
  <!-- 隐藏的上传组件 -->
  <t-upload
    ref="uploadRef"
    :multiple="false"
    :auto-upload="true"
    accept=".mp3,.wav,.txt,.doc,.docx"
    style="display: none"
    :request-method="requestMethod"
    :on-change="handleUploadSuccess"
  />
  <t-dialog v-model:visible="visible" :body="dialogBody" @confirm="visible = !visible"></t-dialog>
</template>

<script lang="ts">
export default {
  name: 'DetailAdvanced',
};
</script>

<script setup lang="ts">
import { Input, MessagePlugin, UploadFile, UploadProps } from 'tdesign-vue-next';
import { h, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';

import { UploadLyrics } from '@/api/LyricsSong';
import { readFileContent } from '@/utils/fileUtil';

const files = ref<UploadProps['value']>([]);
const uploadRef = ref(null);
const tableData = reactive([]);
const router = useRouter();
// 用来保存当前点击的行索引
const currentRowIndex = ref<number | null>(null);
const expandedRowKeys = ref([]);
const options = [
  { label: '云服务器', value: '1' },
  { label: '云数据库', value: '2' },
  { label: '域名注册', value: '3' },
  { label: '网站备案', value: '4' },
  { label: '对象存储', value: '5' },
  { label: '低代码平台', value: '6' },
];

let dialogBody;
const visible = ref<boolean>(false);

const requestMethod: UploadProps['requestMethod'] = () => {
  if (!tableShow.value) {
    tableShow.value = !tableShow.value;
  }
  return new Promise((resolve) => {
    resolve({
      status: 'success',
      response: {
        url: 'https://tdesign.gtimg.com/site/avatar.jpg',
      },
    });
  });
};

const customFileListDisplay = ({ files = [] }: { files: UploadFile[] }) => {
  console.log(router.getRoutes());
  return h('div', [
    h(
      'div',
      {
        class: 'drag-area',
        style: {
          padding: '20px',
          border: '1px dashed #d9d9d9',
          textAlign: 'center',
          backgroundColor: '#fafafa',
          borderRadius: '4px',
          marginBottom: '16px',
          marginTop: '10px',
        },
      },
      files.length === 0 ? '点击上方 “选择文件” 或将文件拖拽到此区域' : '',
    ),
    h(
      'div',
      files.map((file) => h('div', { style: { margin: '4px 0' } }, file.name)),
    ),
  ]);
};

const change = (files, context) => {
  console.log(context);
  const file = files[files.length - 1].raw;
  const fileType = file.name.split('.').pop()?.toLowerCase();
  const data = {
    title: file.name.replace(/\.[^/.]+$/, ''),
  };
  if (fileType === 'txt' || fileType === 'doc' || fileType === 'docx') {
    readFileContent(file, (content) => {
      data.lyrics = content;
      data.lyricsFile = file;
      data.key = tableData.length + 1;
      tableData.push(data);
    });
  } else {
    data.song = file.name;
    data.songFile = file;
    data.key = tableData.length + 1;
    tableData.push(data);
  }
};

const tooltip = () => {
  return h('img', {
    src: 'https://cdn-y.tencentmusic.com/musician/material/cos_806837dca11fdb66c3fcce866b00c23a4a088d69.png',
  });
};

// 处理展开行的逻辑
const handleExpandChange = (expanded: boolean, row: any) => {
  if (expanded) {
    expandedRowKeys.value = [row.key]; // 只展开当前行
  } else {
    expandedRowKeys.value = [];
  }
};

const handleUpload = (rowIndex: number) => {
  // 保存当前点击行的索引
  currentRowIndex.value = rowIndex;
  uploadRef.value?.triggerUpload();
};
const handleUploadSuccess = (files) => {
  const file = files[files.length - 1].raw;
  const fileType = file.name.split('.').pop()?.toLowerCase();
  // 根据返回的结果更新对应的 row 数据
  if (currentRowIndex.value !== null) {
    if (fileType === 'txt' || fileType === 'doc' || fileType === 'docx') {
      readFileContent(file, (content) => {
        tableData[currentRowIndex.value].lyrics = content;
      });
    } else {
      // 假设 response 返回的文件名为 'uploadedFile.mp3'
      // 更新表格数据
      tableData[currentRowIndex.value].song = file.name || 'uploadedFile.mp3';
    }
  }
};

const tableShow = ref(false);

function push() {
  const reqParam = [];
  for (const tableDatum of tableData) {
    if (!tableDatum.originsName || !tableDatum.quote) {
      dialogBody = '请补充完整信息';
      visible.value = true;
    }
    reqParam.push({
      title: tableDatum.title,
      lyrics: tableDatum.lyrics,
      fileName: tableDatum.title,
      cosFileId: 'XXXX.mp3',
      originsName: tableDatum.origins,
      quote: tableDatum.quote,
      language: tableDatum.language,
      lyricists: tableDatum.lyricists,
      songwriter: tableDatum.songwriter,
      tab: tableDatum.tab,
      businessCommissionerId: tableDatum.businessCommissioner,
    });
  }

  UploadLyrics(reqParam)
    .then((response) => {
      // 请求成功地处理逻辑
      console.log(response);
      // router.replace('/result/success').then(() => {
      //   window.location.reload();
      // });
      router.push('/result/success');
    })
    .catch((error) => {
      // 请求失败的处理逻辑
      if (error.response) {
        // 服务端返回的响应错误码
        console.log('Error status:', error.response.status);
        console.log('Error data:', error.response.data);
      } else if (error.request) {
        // 请求已发送，但没有响应
        console.log('No response received:', error.request);
      } else {
        // 其他错误
        console.log('Error message:', error.message);
      }
      MessagePlugin.error('服务器开小差了，请稍后重试');
    });
  console.log(res);
}

const rehandleClickOp = (index) => {
  if (index !== -1) {
    tableData.splice(index, 1);
  }
  tableShow.value = !(tableData.length === 0);
};

const columns = [
  {
    colKey: 'title',
    title: '作品名称',
    width: '350',
    edit: {
      // 1. 支持任意组件。需保证组件包含 `value` 和 `onChange` 两个属性，且 onChange 的第一个参数值为 new value。
      // 2. 如果希望支持校验，组件还需包含 `status` 和 `tips` 属性。具体 API 含义参考 Input 组件
      component: Input,
      // props, 透传全部属性到 Input 组件。可以是一个函数，不同行有不同的 props 属性 时，使用 Function）
      props: {
        clearable: true,
        autofocus: true,
      },
      // 触发校验的时机（when to validate)
      validateTrigger: 'change',
      // 透传给 component: Input 的事件（也可以在 edit.props 中添加）
      on: (editContext) => ({
        onBlur: () => {
          console.log('失去焦点', editContext);
        },
        onEnter: (ctx) => {
          ctx?.e?.preventDefault();
          console.log('onEnter', ctx);
        },
      }),
      // 除了点击非自身元素退出编辑态之外，还有哪些事件退出编辑态
      abortEditOnEvent: ['onEnter'],
      // 编辑完成，退出编辑态后触发
      onEdited: (context) => {
        console.log(context);
        tableData.splice(context.rowIndex, 1, context.newRowData);
        console.log('Edit firstName:', context);
        MessagePlugin.success('Success');
      },
      // 校验规则，此处同 Form 表单。https://tdesign.tencent.com/vue-next/components/form
      rules: [
        { required: true, message: '不能为空' },
        { max: 50, message: '字符数量不能超过 10', type: 'warning' },
      ],
      // 默认是否为编辑状态
      defaultEditable: false,
    },
  },
  {
    colKey: 'lyrics',
    title: '歌词',
    width: '200',
    ellipsis: (h, { row }) => {
      return h(
        'pre',
        {
          style: {
            maxHeight: '300px',
            overflowY: 'scroll',
          },
        },
        row.lyrics,
      );
    },
    cell: (h, { row, rowIndex }: any) => {
      if (row === null || row === undefined || row.lyrics === '' || row.lyrics === null || row.lyrics === undefined) {
        return h(
          'a',
          {
            onClick: () => handleUpload(rowIndex),
            style: {
              color: '#1890ff',
              cursor: 'pointer',
            },
          },
          '上传歌词',
        );
      }
      return row.lyrics;
    },
  },
  {
    colKey: 'song',
    title: '音频文件',
    ellipsis: true,
    width: '200',
    cell: (h, { row, rowIndex }: any) => {
      return row && row.song
        ? row.song
        : h(
            'a',
            {
              onClick: () => handleUpload(rowIndex),
              style: {
                color: '#1890ff',
                cursor: 'pointer',
              },
            },
            '上传音频',
          );
    },
  },
  {
    colKey: 'originsName',
    title: '投递机构',
    width: '200',
    edit: {
      // 1. 支持任意组件。需保证组件包含 `value` 和 `onChange` 两个属性，且 onChange 的第一个参数值为 new value。
      // 2. 如果希望支持校验，组件还需包含 `status` 和 `tips` 属性。具体 API 含义参考 Input 组件
      component: Input,
      // props, 透传全部属性到 Input 组件。可以是一个函数，不同行有不同的 props 属性 时，使用 Function）
      props: {
        clearable: true,
        autofocus: true,
      },
      // 触发校验的时机（when to validate)
      validateTrigger: 'change',
      // 透传给 component: Input 的事件（也可以在 edit.props 中添加）
      on: (editContext) => ({
        onBlur: () => {
          console.log('失去焦点', editContext);
        },
        onEnter: (ctx) => {
          ctx?.e?.preventDefault();
          console.log('onEnter', ctx);
        },
      }),
      // 除了点击非自身元素退出编辑态之外，还有哪些事件退出编辑态
      abortEditOnEvent: ['onEnter'],
      // 编辑完成，退出编辑态后触发
      onEdited: (context) => {
        tableData.splice(context.rowIndex, 1, context.newRowData);
        // MessagePlugin.success('Success');
      },
      // 校验规则，此处同 Form 表单。https://tdesign.tencent.com/vue-next/components/form
      rules: [
        { required: true, message: '不能为空' },
        { max: 50, message: '字符数量不能超过 50', type: 'warning' },
      ],
      // 默认是否为编辑状态
      defaultEditable: true,
    },
  },
  {
    colKey: 'quote',
    title: '报价',
    width: '150',
    edit: {
      // 1. 支持任意组件。需保证组件包含 `value` 和 `onChange` 两个属性，且 onChange 的第一个参数值为 new value。
      // 2. 如果希望支持校验，组件还需包含 `status` 和 `tips` 属性。具体 API 含义参考 Input 组件
      component: Input,
      // props, 透传全部属性到 Input 组件。可以是一个函数，不同行有不同的 props 属性 时，使用 Function）
      props: {
        clearable: true,
        autofocus: true,
        suffix: '元',
        type: 'number',
      },
      // 触发校验的时机（when to validate)
      validateTrigger: 'change',
      // 透传给 component: Input 的事件（也可以在 edit.props 中添加）
      on: (editContext) => ({
        onBlur: () => {
          console.log('失去焦点', editContext);
        },
        onEnter: (ctx) => {
          ctx?.e?.preventDefault();
          console.log('onEnter', ctx);
        },
      }),
      // 除了点击非自身元素退出编辑态之外，还有哪些事件退出编辑态
      abortEditOnEvent: ['onEnter'],
      // 编辑完成，退出编辑态后触发
      onEdited: (context) => {
        tableData.splice(context.rowIndex, 1, context.newRowData);
        // MessagePlugin.success('Success');
      },
      // 校验规则，此处同 Form 表单。https://tdesign.tencent.com/vue-next/components/form
      rules: [{ required: true, message: '不能为空' }],
      // 默认是否为编辑状态
      defaultEditable: true,
    },
  },
  { colKey: 'operation', title: '操作', width: 100, foot: '-' },
];
</script>

<style scoped lang="less">
.upload-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

.upload-container .t-upload {
  width: 80%;
  min-width: 600px;
}

.drag-area {
  background-color: #fafafa;
  border-radius: 4px;
}

.tips {
  color: #8c8c8c;
  font-family:
    PingFang SC,
    serif;
  font-size: 12px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  width: 50%;
}

.upload-div {
  margin-top: 25px;
}

.button-container {
  display: flex;
  justify-content: flex-end;
  width: 100%;
}

:deep(.t-upload__dragger-center) {
  width: 100%;
  border-radius: 12px;
}

:deep(.t-upload__dragger) {
  border: 2px dashed #7daedf; /* 可视化实际的拖拽区域 */
  background-color: rgba(86, 220, 236, 0.1); /* 背景色加透明度 */
}
</style>
