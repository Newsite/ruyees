package ruyees.otp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.action.view.model.VDept;
import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.model.sys.SysOrgElement;

@Service
@Transactional(readOnly = true)
public class SysOrgElementService extends BaseService {

	/**
	 * 根据机构编码获取机构下的所有部门，包括本机构
	 * 
	 * @param orgId
	 * @return
	 */
	public List<SysOrgElement> findAllChildrenById(String orgId) {
		List<SysOrgElement> elements = new ArrayList<SysOrgElement>();
		SysOrgElement element = load(orgId);
		if (element != null) {
			elements.add(element);
			loadAllChildren(element.getFdChildren(), elements);
		}
		return elements;
	}
	
	/**
	 * 根据类型查询部门信息
	 * @param type
	 * @return
	 */
	public List<SysOrgElement> findAllOrgByType(int type){
		return findByCriteria(SysOrgElement.class, Value.eq("fdOrgType", type));
	}

	

	/**
	 * FDID,FD_ORG_TYPE,FD_NAME,FD_NO,FD_PARENTID
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<VDept> findOrgByEelementName(String key, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("type", type);
		List<Map> values = findByNamedQuery("findOrgByEelementName", map,
				Map.class);
		List<VDept> sysOrgPersons = new ArrayList<VDept>();
		VDept element = null;
		for (Map<String, Object> value : values) {
			element = new VDept(value.get("FDID").toString(), value.get(
					"FD_NAME").toString());
			sysOrgPersons.add(element);
		}
		return sysOrgPersons;
	}

	/**
	 * FDID,FD_ORG_TYPE,FD_NAME,FD_NO,FD_PARENTID
	 * 
	 * @param key
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<VDept> findDeptByOrgIdAndName(String key, String parentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("parentId", parentId);
		List<Map> values = findByNamedQuery("findDeptByOrgIdAndDeptName", map,
				Map.class);
		List<VDept> sysOrgPersons = new ArrayList<VDept>();
		VDept element = null;
		for (Map<String, Object> value : values) {
			element = new VDept(value.get("FDID").toString(), value.get(
					"FD_NAME").toString());
			sysOrgPersons.add(element);
		}
		return sysOrgPersons;
	}

	private void loadAllChildren(List<SysOrgElement> elements,
			List<SysOrgElement> targetEelements) {
		for (SysOrgElement e : elements) {
			targetEelements.add(e);
			if (e.getHbmChildren() != null) {
				loadAllChildren(e.getFdChildren(), targetEelements);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<SysOrgElement> getEntityClass() {
		return SysOrgElement.class;
	}

}
