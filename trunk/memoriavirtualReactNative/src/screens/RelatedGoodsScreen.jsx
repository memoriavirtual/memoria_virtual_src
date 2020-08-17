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

const RelatedGoodsScreen = () => {
  const { state: { translate } } = useContext(I18nContext);

  // Mock relatedGoods
  const relatedGoods = [
    {
      key: '1',
      value: 'Sibipiruna 1',
    },
    {
      key: '2',
      value: 'Sibipiruna 2',
    },
  ];

  return (
    <ScrollView contentContainerStyle={styles.generalPurposeForm}>
      <Title titleText="Sibipiruna" />
      {relatedGoods.map((item) => (
        <ItemCard
          type={itemCardTypes.RELATED_GOOD}
          title={translate('RELATED_GOODS_RELATED_GOOD')}
          value={item.value}
        />
      ))}
    </ScrollView>
  );
};

RelatedGoodsScreen.navigationOptions = () => ({
  headerTitle: 'Bens Relacionados',
});

export default RelatedGoodsScreen;
