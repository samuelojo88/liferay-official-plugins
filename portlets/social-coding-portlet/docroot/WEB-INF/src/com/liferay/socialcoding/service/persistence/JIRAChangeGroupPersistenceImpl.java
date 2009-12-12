/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.socialcoding.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.socialcoding.NoSuchJIRAChangeGroupException;
import com.liferay.socialcoding.model.JIRAChangeGroup;
import com.liferay.socialcoding.model.impl.JIRAChangeGroupImpl;
import com.liferay.socialcoding.model.impl.JIRAChangeGroupModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JIRAChangeGroupPersistenceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class JIRAChangeGroupPersistenceImpl extends BasePersistenceImpl<JIRAChangeGroup>
	implements JIRAChangeGroupPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JIRAChangeGroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_JIRAUSERID = new FinderPath(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByJiraUserId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_JIRAUSERID = new FinderPath(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByJiraUserId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_JIRAUSERID = new FinderPath(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByJiraUserId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_JIRAISSUEID = new FinderPath(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByJiraIssueId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_JIRAISSUEID = new FinderPath(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByJiraIssueId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_JIRAISSUEID = new FinderPath(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByJiraIssueId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(JIRAChangeGroup jiraChangeGroup) {
		EntityCacheUtil.putResult(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupImpl.class, jiraChangeGroup.getPrimaryKey(),
			jiraChangeGroup);
	}

	public void cacheResult(List<JIRAChangeGroup> jiraChangeGroups) {
		for (JIRAChangeGroup jiraChangeGroup : jiraChangeGroups) {
			if (EntityCacheUtil.getResult(
						JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
						JIRAChangeGroupImpl.class,
						jiraChangeGroup.getPrimaryKey(), this) == null) {
				cacheResult(jiraChangeGroup);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JIRAChangeGroupImpl.class.getName());
		EntityCacheUtil.clearCache(JIRAChangeGroupImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public JIRAChangeGroup create(long jiraChangeGroupId) {
		JIRAChangeGroup jiraChangeGroup = new JIRAChangeGroupImpl();

		jiraChangeGroup.setNew(true);
		jiraChangeGroup.setPrimaryKey(jiraChangeGroupId);

		return jiraChangeGroup;
	}

	public JIRAChangeGroup remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JIRAChangeGroup remove(long jiraChangeGroupId)
		throws NoSuchJIRAChangeGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JIRAChangeGroup jiraChangeGroup = (JIRAChangeGroup)session.get(JIRAChangeGroupImpl.class,
					new Long(jiraChangeGroupId));

			if (jiraChangeGroup == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						jiraChangeGroupId);
				}

				throw new NoSuchJIRAChangeGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					jiraChangeGroupId);
			}

			return remove(jiraChangeGroup);
		}
		catch (NoSuchJIRAChangeGroupException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JIRAChangeGroup remove(JIRAChangeGroup jiraChangeGroup)
		throws SystemException {
		for (ModelListener<JIRAChangeGroup> listener : listeners) {
			listener.onBeforeRemove(jiraChangeGroup);
		}

		jiraChangeGroup = removeImpl(jiraChangeGroup);

		for (ModelListener<JIRAChangeGroup> listener : listeners) {
			listener.onAfterRemove(jiraChangeGroup);
		}

		return jiraChangeGroup;
	}

	protected JIRAChangeGroup removeImpl(JIRAChangeGroup jiraChangeGroup)
		throws SystemException {
		jiraChangeGroup = toUnwrappedModel(jiraChangeGroup);

		Session session = null;

		try {
			session = openSession();

			if (jiraChangeGroup.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JIRAChangeGroupImpl.class,
						jiraChangeGroup.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(jiraChangeGroup);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupImpl.class, jiraChangeGroup.getPrimaryKey());

		return jiraChangeGroup;
	}

	public JIRAChangeGroup updateImpl(
		com.liferay.socialcoding.model.JIRAChangeGroup jiraChangeGroup,
		boolean merge) throws SystemException {
		jiraChangeGroup = toUnwrappedModel(jiraChangeGroup);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, jiraChangeGroup, merge);

			jiraChangeGroup.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
			JIRAChangeGroupImpl.class, jiraChangeGroup.getPrimaryKey(),
			jiraChangeGroup);

		return jiraChangeGroup;
	}

	protected JIRAChangeGroup toUnwrappedModel(JIRAChangeGroup jiraChangeGroup) {
		if (jiraChangeGroup instanceof JIRAChangeGroupImpl) {
			return jiraChangeGroup;
		}

		JIRAChangeGroupImpl jiraChangeGroupImpl = new JIRAChangeGroupImpl();

		jiraChangeGroupImpl.setNew(jiraChangeGroup.isNew());
		jiraChangeGroupImpl.setPrimaryKey(jiraChangeGroup.getPrimaryKey());

		jiraChangeGroupImpl.setJiraChangeGroupId(jiraChangeGroup.getJiraChangeGroupId());
		jiraChangeGroupImpl.setJiraUserId(jiraChangeGroup.getJiraUserId());
		jiraChangeGroupImpl.setCreateDate(jiraChangeGroup.getCreateDate());
		jiraChangeGroupImpl.setJiraIssueId(jiraChangeGroup.getJiraIssueId());

		return jiraChangeGroupImpl;
	}

	public JIRAChangeGroup findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JIRAChangeGroup findByPrimaryKey(long jiraChangeGroupId)
		throws NoSuchJIRAChangeGroupException, SystemException {
		JIRAChangeGroup jiraChangeGroup = fetchByPrimaryKey(jiraChangeGroupId);

		if (jiraChangeGroup == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + jiraChangeGroupId);
			}

			throw new NoSuchJIRAChangeGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				jiraChangeGroupId);
		}

		return jiraChangeGroup;
	}

	public JIRAChangeGroup fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JIRAChangeGroup fetchByPrimaryKey(long jiraChangeGroupId)
		throws SystemException {
		JIRAChangeGroup jiraChangeGroup = (JIRAChangeGroup)EntityCacheUtil.getResult(JIRAChangeGroupModelImpl.ENTITY_CACHE_ENABLED,
				JIRAChangeGroupImpl.class, jiraChangeGroupId, this);

		if (jiraChangeGroup == null) {
			Session session = null;

			try {
				session = openSession();

				jiraChangeGroup = (JIRAChangeGroup)session.get(JIRAChangeGroupImpl.class,
						new Long(jiraChangeGroupId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (jiraChangeGroup != null) {
					cacheResult(jiraChangeGroup);
				}

				closeSession(session);
			}
		}

		return jiraChangeGroup;
	}

	public List<JIRAChangeGroup> findByJiraUserId(String jiraUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { jiraUserId };

		List<JIRAChangeGroup> list = (List<JIRAChangeGroup>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_JIRAUSERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JIRACHANGEGROUP_WHERE);

				if (jiraUserId == null) {
					query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_1);
				}
				else {
					if (jiraUserId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_3);
					}
					else {
						query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_2);
					}
				}

				query.append(JIRAChangeGroupModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (jiraUserId != null) {
					qPos.add(jiraUserId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JIRAChangeGroup>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_JIRAUSERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JIRAChangeGroup> findByJiraUserId(String jiraUserId, int start,
		int end) throws SystemException {
		return findByJiraUserId(jiraUserId, start, end, null);
	}

	public List<JIRAChangeGroup> findByJiraUserId(String jiraUserId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				jiraUserId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JIRAChangeGroup> list = (List<JIRAChangeGroup>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_JIRAUSERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_JIRACHANGEGROUP_WHERE);

				if (jiraUserId == null) {
					query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_1);
				}
				else {
					if (jiraUserId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_3);
					}
					else {
						query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(JIRAChangeGroupModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (jiraUserId != null) {
					qPos.add(jiraUserId);
				}

				list = (List<JIRAChangeGroup>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JIRAChangeGroup>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_JIRAUSERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JIRAChangeGroup findByJiraUserId_First(String jiraUserId,
		OrderByComparator obc)
		throws NoSuchJIRAChangeGroupException, SystemException {
		List<JIRAChangeGroup> list = findByJiraUserId(jiraUserId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("jiraUserId=");
			msg.append(jiraUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchJIRAChangeGroupException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JIRAChangeGroup findByJiraUserId_Last(String jiraUserId,
		OrderByComparator obc)
		throws NoSuchJIRAChangeGroupException, SystemException {
		int count = countByJiraUserId(jiraUserId);

		List<JIRAChangeGroup> list = findByJiraUserId(jiraUserId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("jiraUserId=");
			msg.append(jiraUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchJIRAChangeGroupException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JIRAChangeGroup[] findByJiraUserId_PrevAndNext(
		long jiraChangeGroupId, String jiraUserId, OrderByComparator obc)
		throws NoSuchJIRAChangeGroupException, SystemException {
		JIRAChangeGroup jiraChangeGroup = findByPrimaryKey(jiraChangeGroupId);

		int count = countByJiraUserId(jiraUserId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JIRACHANGEGROUP_WHERE);

			if (jiraUserId == null) {
				query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_1);
			}
			else {
				if (jiraUserId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_3);
				}
				else {
					query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(JIRAChangeGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (jiraUserId != null) {
				qPos.add(jiraUserId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					jiraChangeGroup);

			JIRAChangeGroup[] array = new JIRAChangeGroupImpl[3];

			array[0] = (JIRAChangeGroup)objArray[0];
			array[1] = (JIRAChangeGroup)objArray[1];
			array[2] = (JIRAChangeGroup)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JIRAChangeGroup> findByJiraIssueId(long jiraIssueId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(jiraIssueId) };

		List<JIRAChangeGroup> list = (List<JIRAChangeGroup>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_JIRAISSUEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JIRACHANGEGROUP_WHERE);

				query.append(_FINDER_COLUMN_JIRAISSUEID_JIRAISSUEID_2);

				query.append(JIRAChangeGroupModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(jiraIssueId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JIRAChangeGroup>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_JIRAISSUEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JIRAChangeGroup> findByJiraIssueId(long jiraIssueId, int start,
		int end) throws SystemException {
		return findByJiraIssueId(jiraIssueId, start, end, null);
	}

	public List<JIRAChangeGroup> findByJiraIssueId(long jiraIssueId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(jiraIssueId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JIRAChangeGroup> list = (List<JIRAChangeGroup>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_JIRAISSUEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_JIRACHANGEGROUP_WHERE);

				query.append(_FINDER_COLUMN_JIRAISSUEID_JIRAISSUEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(JIRAChangeGroupModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(jiraIssueId);

				list = (List<JIRAChangeGroup>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JIRAChangeGroup>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_JIRAISSUEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JIRAChangeGroup findByJiraIssueId_First(long jiraIssueId,
		OrderByComparator obc)
		throws NoSuchJIRAChangeGroupException, SystemException {
		List<JIRAChangeGroup> list = findByJiraIssueId(jiraIssueId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("jiraIssueId=");
			msg.append(jiraIssueId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchJIRAChangeGroupException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JIRAChangeGroup findByJiraIssueId_Last(long jiraIssueId,
		OrderByComparator obc)
		throws NoSuchJIRAChangeGroupException, SystemException {
		int count = countByJiraIssueId(jiraIssueId);

		List<JIRAChangeGroup> list = findByJiraIssueId(jiraIssueId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("jiraIssueId=");
			msg.append(jiraIssueId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchJIRAChangeGroupException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JIRAChangeGroup[] findByJiraIssueId_PrevAndNext(
		long jiraChangeGroupId, long jiraIssueId, OrderByComparator obc)
		throws NoSuchJIRAChangeGroupException, SystemException {
		JIRAChangeGroup jiraChangeGroup = findByPrimaryKey(jiraChangeGroupId);

		int count = countByJiraIssueId(jiraIssueId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JIRACHANGEGROUP_WHERE);

			query.append(_FINDER_COLUMN_JIRAISSUEID_JIRAISSUEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(JIRAChangeGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(jiraIssueId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					jiraChangeGroup);

			JIRAChangeGroup[] array = new JIRAChangeGroupImpl[3];

			array[0] = (JIRAChangeGroup)objArray[0];
			array[1] = (JIRAChangeGroup)objArray[1];
			array[2] = (JIRAChangeGroup)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JIRAChangeGroup> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JIRAChangeGroup> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JIRAChangeGroup> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JIRAChangeGroup> list = (List<JIRAChangeGroup>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_JIRACHANGEGROUP);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_JIRACHANGEGROUP.concat(JIRAChangeGroupModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<JIRAChangeGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JIRAChangeGroup>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JIRAChangeGroup>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByJiraUserId(String jiraUserId) throws SystemException {
		for (JIRAChangeGroup jiraChangeGroup : findByJiraUserId(jiraUserId)) {
			remove(jiraChangeGroup);
		}
	}

	public void removeByJiraIssueId(long jiraIssueId) throws SystemException {
		for (JIRAChangeGroup jiraChangeGroup : findByJiraIssueId(jiraIssueId)) {
			remove(jiraChangeGroup);
		}
	}

	public void removeAll() throws SystemException {
		for (JIRAChangeGroup jiraChangeGroup : findAll()) {
			remove(jiraChangeGroup);
		}
	}

	public int countByJiraUserId(String jiraUserId) throws SystemException {
		Object[] finderArgs = new Object[] { jiraUserId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_JIRAUSERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JIRACHANGEGROUP_WHERE);

				if (jiraUserId == null) {
					query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_1);
				}
				else {
					if (jiraUserId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_3);
					}
					else {
						query.append(_FINDER_COLUMN_JIRAUSERID_JIRAUSERID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (jiraUserId != null) {
					qPos.add(jiraUserId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_JIRAUSERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByJiraIssueId(long jiraIssueId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(jiraIssueId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_JIRAISSUEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JIRACHANGEGROUP_WHERE);

				query.append(_FINDER_COLUMN_JIRAISSUEID_JIRAISSUEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(jiraIssueId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_JIRAISSUEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JIRACHANGEGROUP);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.socialcoding.model.JIRAChangeGroup")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JIRAChangeGroup>> listenersList = new ArrayList<ModelListener<JIRAChangeGroup>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JIRAChangeGroup>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.socialcoding.service.persistence.JIRAActionPersistence")
	protected com.liferay.socialcoding.service.persistence.JIRAActionPersistence jiraActionPersistence;
	@BeanReference(name = "com.liferay.socialcoding.service.persistence.JIRAChangeGroupPersistence")
	protected com.liferay.socialcoding.service.persistence.JIRAChangeGroupPersistence jiraChangeGroupPersistence;
	@BeanReference(name = "com.liferay.socialcoding.service.persistence.JIRAChangeItemPersistence")
	protected com.liferay.socialcoding.service.persistence.JIRAChangeItemPersistence jiraChangeItemPersistence;
	@BeanReference(name = "com.liferay.socialcoding.service.persistence.JIRAIssuePersistence")
	protected com.liferay.socialcoding.service.persistence.JIRAIssuePersistence jiraIssuePersistence;
	@BeanReference(name = "com.liferay.socialcoding.service.persistence.SVNRepositoryPersistence")
	protected com.liferay.socialcoding.service.persistence.SVNRepositoryPersistence svnRepositoryPersistence;
	@BeanReference(name = "com.liferay.socialcoding.service.persistence.SVNRevisionPersistence")
	protected com.liferay.socialcoding.service.persistence.SVNRevisionPersistence svnRevisionPersistence;
	private static final String _SQL_SELECT_JIRACHANGEGROUP = "SELECT jiraChangeGroup FROM JIRAChangeGroup jiraChangeGroup";
	private static final String _SQL_SELECT_JIRACHANGEGROUP_WHERE = "SELECT jiraChangeGroup FROM JIRAChangeGroup jiraChangeGroup WHERE ";
	private static final String _SQL_COUNT_JIRACHANGEGROUP = "SELECT COUNT(jiraChangeGroup) FROM JIRAChangeGroup jiraChangeGroup";
	private static final String _SQL_COUNT_JIRACHANGEGROUP_WHERE = "SELECT COUNT(jiraChangeGroup) FROM JIRAChangeGroup jiraChangeGroup WHERE ";
	private static final String _FINDER_COLUMN_JIRAUSERID_JIRAUSERID_1 = "jiraChangeGroup.jiraUserId IS NULL";
	private static final String _FINDER_COLUMN_JIRAUSERID_JIRAUSERID_2 = "jiraChangeGroup.jiraUserId = ?";
	private static final String _FINDER_COLUMN_JIRAUSERID_JIRAUSERID_3 = "(jiraChangeGroup.jiraUserId IS NULL OR jiraChangeGroup.jiraUserId = ?)";
	private static final String _FINDER_COLUMN_JIRAISSUEID_JIRAISSUEID_2 = "jiraChangeGroup.jiraIssueId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "jiraChangeGroup.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JIRAChangeGroup exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JIRAChangeGroup exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(JIRAChangeGroupPersistenceImpl.class);
}