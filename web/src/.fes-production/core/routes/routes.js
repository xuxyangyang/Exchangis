import { createRouter as createVueRouter, createWebHashHistory, ApplyPluginsType } from 'D:/developsoft/gitfile/WeDataSphere/Exchangis/web/node_modules/@fesjs/runtime';
import { plugin } from '@@/core/plugin';

export function getRoutes() {
  const routes = [
  {
    "path": "/",
    "component": require('D:/developsoft/gitfile/WeDataSphere/Exchangis/web/src/.fes-production/plugin-layout/index.js').default,
    "children": [
      {
        "path": "/",
        "redirect": "/projectManage"
      },
      {
        "path": "/projectManage",
        "component": require('@/pages/projectManage').default,
        "meta": {
          "name": "projectManage",
          "title": "globalMenu.projectManage"
        }
      },
      {
        "path": "/dataSourceManage",
        "component": require('@/pages/dataSourceManage').default,
        "meta": {
          "name": "dataSourceManage",
          "title": "globalMenu.dataSourceManage"
        }
      },
      {
        "path": "/jobManagement",
        "component": require('@/pages/jobManagement').default,
        "meta": {
          "name": "jobManagement",
          "title": "globalMenu.jobManagement"
        }
      },
      {
        "path": "/synchronizationHistory",
        "component": require('@/pages/synchronizationHistory').default,
        "meta": {
          "name": "synchronizationHistory",
          "title": "globalMenu.synchronizationHistory"
        }
      },
      {
        "path": "/homePage",
        "component": require('@/pages/homePage').default,
        "meta": {
          "name": "homePage",
          "title": "globalMenu.homePage"
        }
      },
      {
        "path": "/childJobManagement",
        "component": require('@/pages/jobManagement/spaIndex').default,
        "meta": {
          "name": "synchronizationHistory",
          "title": "globalMenu.synchronizationHistory"
        }
      }
    ]
  }
];
  return routes;
}

const ROUTER_BASE = '';
let router = null;
let history = null;
export const createRouter = (routes) => {
  if (router) {
    return router;
  }
  const createHistory = plugin.applyPlugins({
    key: 'modifyCreateHistory',
    type: ApplyPluginsType.modify,
    args: {
      base: ROUTER_BASE
    },
    initialValue: createWebHashHistory,
  });
  history = createHistory(ROUTER_BASE);
  // 修改routes
  plugin.applyPlugins({
    key: 'patchRoutes',
    type: ApplyPluginsType.event,
    args: { routes },
  });
  router = createVueRouter({
    history,
    routes
  });

  plugin.applyPlugins({
    key: 'onRouterCreated',
    type: ApplyPluginsType.event,
    args: { router },
  });

  return router;
};

export const getRouter = ()=>{
    if(!router){
        console.warn(`[preset-build-in] router is null`)
    }
    return router;
}

export const getHistory = ()=>{
    if(!history){
        console.warn(`[preset-build-in] history is null`)
    }
    return history;
}

export const destroyRouter = ()=>{
    router = null;
    history = null;
}

export const defineRouteMeta = (param)=>{
    return param
}
