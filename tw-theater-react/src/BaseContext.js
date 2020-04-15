import React from 'react';

const GENRES = ["剧情", "爱情", "喜剧", "犯罪", "奇幻", "冒险", "动作", "动画", "悬疑", "科幻"];
const API_ROOT = "http://cloud.truman.pro:15000";
const BaseContext = React.createContext({API_ROOT, GENRES});

export default BaseContext;