package com.yichan.gaotezhipei.trainservice.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yichan.gaotezhipei.trainservice.entity.CourseSectionPo;
import com.yichan.gaotezhipei.trainservice.entity.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 2018/2/8 0008.
 */

public class CourseOutlineAdapter extends BaseAdapter {
    protected static final int VIEW_TYPE_SECTION = 1;
    protected static final int VIEW_TYPE_CELL = 0;
    /* Add Empty module type */
    protected static final int VIEW_TYPE_EMPTY = -1;

    protected Context mContext;

    protected List<Module> moduleList;
    protected List dataList = new ArrayList();
    private boolean isLearnModal = false;

    public CourseOutlineAdapter(Context context) {
        mContext = context;
    }

    public CourseOutlineAdapter(Context context, List<Module> moduleList) {
        mContext = context;
        this.moduleList = moduleList;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = VIEW_TYPE_CELL;
        Object obj = dataList.get(position);
        if (obj instanceof Module) {
            viewType = VIEW_TYPE_SECTION;
        }
        return viewType;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View cell = null;
        if (getItemViewType(position) == VIEW_TYPE_SECTION) {
            cell = getSectionView(position, convertView, parent);
        } else {
            int positionInModule = -1;
            Module module = null;
            for (int i = 0; i <= position; i++) {
                Object obj = dataList.get(i);
                if (obj instanceof Module) {
                    module = (Module) obj;
                    positionInModule = -1;
                } else {
                    positionInModule++;
                }
            }
            cell = getItemView(position, convertView, parent, module, positionInModule);
        }

        return cell;
    }

    protected View getSectionView(int position, View convertView, ViewGroup parent) {
        CompModuleHeader sectionView = null;
        if (convertView != null && convertView instanceof CompModuleHeader) {
            sectionView = (CompModuleHeader) convertView;
        } else {
            sectionView = new CompModuleHeader(mContext);
            //sectionView.setCompModuleHeaderListener(mCompModuleHeaderListener);
        }

        Module module = (Module) dataList.get(position);
        //sectionView.setTag(R.id.tag_module, module);
        if(isLearnModal)
            sectionView.setLeftImgHidden(true);
        String moduleTitle = module.getModuleId();
        sectionView.setHeaderTitle(moduleTitle);

        return sectionView;
    }

    protected View getItemView(int position, View convertView, ViewGroup parent, Module module, int positionInModule) {
        View view = null;

        CompModuleItem cell = null;
        if (convertView != null && convertView instanceof CompModuleItem) {
            cell = (CompModuleItem) convertView;
        } else {
            cell = new CompModuleItem(mContext);
        }
        List<CourseSectionPo> courseSectionPos = module.getList();
        //cell.setTag(R.id.tag_module, courseSectionPos.get(positionInModule));
        cell.setTitle(String.format("%d-%d %s", courseSectionPos.get(positionInModule).getChapterId(),
                courseSectionPos.get(positionInModule).getNumber(),
                courseSectionPos.get(positionInModule).getSectionName()));
        if(isLearnModal) {
            cell.setMore("02:29");
            if ("第1章 课程简介".equals(module.getModuleId()))
                cell.setStatus(true);
        }
        view = cell;

        return view;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        setModuleList(moduleList, false);
    }

    public void setModuleList(List<Module> moduleList, boolean cacheAble) {
        this.moduleList = moduleList;
        dataList = new ArrayList();
        for (Module module : moduleList) {
            dataList.add(module);

            for(int i=0;i<module.getList().size();i++) {
                Object obj = module.getList().get(i);
                dataList.add(obj);
            }
        }
        this.notifyDataSetChanged();
    }

    public void setLearnModal(boolean learnModal) {
        isLearnModal = learnModal;
    }
}
