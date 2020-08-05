import createDataContext from './createDataContext';
import PT from '../i18n/locales/pt.json';

const translations = {
  pt: PT,
};

const getTranslate = (langCode) => (key) => translations[langCode][key] || key;

const i18nReducer = (state, action) => {
  switch (action.type) {
    case 'setLanguage':
      return {
        ...state,
        langCode: action.payload,
        translate: getTranslate(action.payload),
      };
    default:
      return { ...state };
  }
};

const setLanguage = (dispatch) => (newLangCode) => {
  dispatch({ type: 'setLanguage', payload: newLangCode });
};

export const { Provider, Context } = createDataContext(
  i18nReducer,
  { setLanguage },
  { langCode: 'pt', translate: getTranslate('pt') },
);
