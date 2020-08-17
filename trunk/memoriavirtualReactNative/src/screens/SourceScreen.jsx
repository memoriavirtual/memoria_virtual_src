import React, {
  useContext,
  useReducer,
  useEffect,
} from 'react';
import { ScrollView, StyleSheet } from 'react-native';

import generalinfoStyles from '../styles/generalinfo';
import itemCardTypes from '../enums/itemCardTypes';

import Title from '../components/Title';
import ItemCard from '../components/ItemCard';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const SourceScreen = () => {
  const { state: { translate } } = useContext(I18nContext);
  const [state, setState] = useReducer(
    (prevState, newState) => ({ ...prevState, ...newState }),
    {
      acquisitionType: 'aaaa',
      venalValue: 'aaaa',
      date: 'aaaa',
      firstOwner: 'aaaa',
      transactionDate: 'aaaa',
      history: 'aaaa',
      researchTool: 'aaaa',
    },
  );

  useEffect(() => {
    setState({
      acquisitionType: 'aaaa',
      venalValue: 'aaaa',
      date: 'aaaa',
      firstOwner: 'aaaa',
      transactionDate: 'aaaa',
      history: 'aaaa',
      researchTool: 'aaaa',
    });
  }, []);

  return (
    <ScrollView contentContainerStyle={styles.generalPurposeForm}>
      <Title titleText="Sibipiruna" />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('SOURCE_ACQUISITION_TYPE')}
        value={state.acquisitionType}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('SOURCE_VENAL_VALUE')}
        value={state.venalValue}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DATE')}
        value={state.date}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('SOURCE_FIRST_OWNER')}
        value={state.firstOwner}
      />
      <ItemCard
        type={itemCardTypes.LARGE_TEXT}
        title={translate('SOURCE_TRANSACTION_DATE')}
        value={state.transactionDate}
      />
      <ItemCard
        type={itemCardTypes.LARGE_TEXT}
        title={translate('SOURCE_HISTORY')}
        value={state.history}
      />
      <ItemCard
        type={itemCardTypes.LARGE_TEXT}
        title={translate('SOURCE_RESEARCH_TOOL')}
        value={state.researchTool}
      />
    </ScrollView>
  );
};

SourceScreen.navigationOptions = () => ({
  headerTitle: 'Procedência',
});

export default SourceScreen;
