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

import { IGameLibrary, defaultValue } from 'app/shared/model/game-library.model';

export const ACTION_TYPES = {
  FETCH_GAMELIBRARY_LIST: 'gameLibrary/FETCH_GAMELIBRARY_LIST',
  FETCH_GAMELIBRARY: 'gameLibrary/FETCH_GAMELIBRARY',
  CREATE_GAMELIBRARY: 'gameLibrary/CREATE_GAMELIBRARY',
  UPDATE_GAMELIBRARY: 'gameLibrary/UPDATE_GAMELIBRARY',
  DELETE_GAMELIBRARY: 'gameLibrary/DELETE_GAMELIBRARY',
  RESET: 'gameLibrary/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGameLibrary>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type GameLibraryState = Readonly<typeof initialState>;

// Reducer

export default (state: GameLibraryState = initialState, action): GameLibraryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMELIBRARY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMELIBRARY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMELIBRARY):
    case REQUEST(ACTION_TYPES.UPDATE_GAMELIBRARY):
    case REQUEST(ACTION_TYPES.DELETE_GAMELIBRARY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMELIBRARY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMELIBRARY):
    case FAILURE(ACTION_TYPES.CREATE_GAMELIBRARY):
    case FAILURE(ACTION_TYPES.UPDATE_GAMELIBRARY):
    case FAILURE(ACTION_TYPES.DELETE_GAMELIBRARY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMELIBRARY_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_GAMELIBRARY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMELIBRARY):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMELIBRARY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMELIBRARY):
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

const apiUrl = 'api/game-libraries';

// Actions

export const getEntities: ICrudGetAllAction<IGameLibrary> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMELIBRARY_LIST,
    payload: axios.get<IGameLibrary>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IGameLibrary> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMELIBRARY,
    payload: axios.get<IGameLibrary>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IGameLibrary> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMELIBRARY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IGameLibrary> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMELIBRARY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGameLibrary> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMELIBRARY,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
