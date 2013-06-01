package ruyees.otp.dao.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;

import ruyees.otp.model.BamProcess;
import ruyees.otp.service.flow.BamProjectPhaseService;

public class ProceeIntercept extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private BamProjectPhaseService bamProjectPhaseService;

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if (entity instanceof BamProcess) {
			BamProcess process = (BamProcess) entity;
			if (!process.getThrough())
				return false;
			// bamProjectPhaseService.setPhaseStatus(process);
		}
		return super.onFlushDirty(entity, id, currentState, previousState,
				propertyNames, types);
	}
}
