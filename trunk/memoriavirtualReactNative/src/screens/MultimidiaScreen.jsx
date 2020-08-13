import React, { useContext } from 'react';
import { StyleSheet, ScrollView } from 'react-native';

import generalinfoStyles from '../styles/generalinfo';

import itemCardTypes from '../enums/itemCardTypes';

import Title from '../components/Title';
import ItemCard from '../components/ItemCard';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const MultimidiaScreen = () => {
  const { state: { translate } } = useContext(I18nContext);

  // Mock relatedGoods
  const relatedGoods = [
    {
      key: '1',
      name: 'Sibipiruna 1',
      description: 'Árvore do ICMC',
      source: require('../assets/sibipiruna_mock.jpg'),
    },

  ];

  return (
    <ScrollView contentContainerStyle={styles.generalPurposeForm}>
      <Title titleText="Sibipiruna" />
      {relatedGoods.map((item) => (
        <ItemCard
          type={itemCardTypes.MULTIMIDIA}
          title={translate('MULTIMIDIA_MULTIMIDIA')}
          value={item}
        />
      ))}
    </ScrollView>
  );
};

MultimidiaScreen.navigationOptions = () => ({
  headerTitle: 'Bens Relacionados',
});

export default MultimidiaScreen;
