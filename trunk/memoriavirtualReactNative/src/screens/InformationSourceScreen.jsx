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

const InformationSourceScreen = () => {
  const { state: { translate } } = useContext(I18nContext);

  // Mock sources
  const sources = [
    {
      key: '1',
      value: 'http://www.cobit.xpg.com.br/microspc/nexus2600.htm',
    },
  ];

  return (
    <ScrollView contentContainerStyle={styles.generalPurposeForm}>
      <Title titleText="Sibipiruna" />
      {sources.map((item) => (
        <ItemCard
          type={itemCardTypes.TEXT}
          title={translate('INFO_SOURCE_SOURCE')}
          value={item.value}
        />
      ))}
    </ScrollView>
  );
};

InformationSourceScreen.navigationOptions = () => ({
  headerTitle: 'Fontes de Informação',
});

export default InformationSourceScreen;
