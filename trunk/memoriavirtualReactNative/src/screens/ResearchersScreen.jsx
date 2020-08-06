import React, { useContext } from 'react';
import { ScrollView, StyleSheet } from 'react-native';

import generalinfoStyles from '../styles/generalinfo';
import itemCardTypes from '../enums/itemCardTypes';

import Title from '../components/Title';
import ItemCard from '../components/ItemCard';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const ResearchersScreen = () => {
  const { state: { translate } } = useContext(I18nContext);

  // Mock sources
  const sources = [
    {
      key: '1',
      date: '30/03/2016',
      researcher: 'Elisa',
      notes: 'Notas',
    },
    {
      key: '2',
      date: '30/07/2020',
      researcher: 'Thiago',
      notes: 'Notas 2',
    }
  ];

  return (
    <ScrollView contentContainerStyle={styles.generalPurposeForm}>
      <Title titleText="Sibipiruna" />
      {sources.map((item) => (
        <ItemCard
          type={itemCardTypes.RESEARCHER}
          title={translate('RESEARCHERS_RESEARCHER')}
          researchItem={item}
        />
      ))}
    </ScrollView>
  );
};

ResearchersScreen.navigationOptions = () => ({
  headerTitle: 'Pesquisadores',
});

export default ResearchersScreen;