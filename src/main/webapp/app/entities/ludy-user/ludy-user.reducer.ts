import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILudyUser, defaultValue } from 'app/shared/model/ludy-user.model';

export const ACTION_TYPES = {
  FETCH_LUDYUSER_LIST: 'ludyUser/FETCH_LUDYUSER_LIST',
  FETCH_LUDYUSER: 'ludyUser/FETCH_LUDYUSER',
  CREATE_LUDYUSER: 'ludyUser/CREATE_LUDYUSER',
  UPDATE_LUDYUSER: 'ludyUser/UPDATE_LUDYUSER',
  DELETE_LUDYUSER: 'ludyUser/DELETE_LUDYUSER',
  RESET: 'ludyUser/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILudyUser>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type LudyUserState = Readonly<typeof initialState>;

// Reducer

export default (state: LudyUserState = initialState, action): LudyUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LUDYUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LUDYUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LUDYUSER):
    case REQUEST(ACTION_TYPES.UPDATE_LUDYUSER):
    case REQUEST(ACTION_TYPES.DELETE_LUDYUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LUDYUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LUDYUSER):
    case FAILURE(ACTION_TYPES.CREATE_LUDYUSER):
    case FAILURE(ACTION_TYPES.UPDATE_LUDYUSER):
    case FAILURE(ACTION_TYPES.DELETE_LUDYUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LUDYUSER_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_LUDYUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LUDYUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_LUDYUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LUDYUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/ludy-users';

// Actions

export const getEntities: ICrudGetAllAction<ILudyUser> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LUDYUSER_LIST,
    payload: axios.get<ILudyUser>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ILudyUser> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LUDYUSER,
    payload: axios.get<ILudyUser>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILudyUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LUDYUSER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ILudyUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LUDYUSER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILudyUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LUDYUSER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
