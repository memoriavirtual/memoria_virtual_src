import React, { useContext, useReducer } from 'react';
import { StyleSheet, ScrollView, Text } from 'react-native';

import Title from '../components/Title';
import ItemCard from '../components/ItemCard';

import generalinfoStyles from '../styles/generalinfo';

import * as labels from '../constants/description';
import itemCardTypes from '../enums/itemCardTypes';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const DescriptionScreen = () => {
  const { state: { translate } } = useContext(I18nContext);
  const [state, setState] = useReducer(
    (prevState, newState) => ({ ...prevState, ...newState }),
    {
       characteristics: 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
       dimensions: 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
       topoConditions: 'aaaaa',
       use: 'aaaa',
       numberEnv: '0',
       numberFloor: '0',
       alcove: false,
       basement: false,
       attic: false,
       anthropic: '',
       envCharacteristics: '',
       landscapeSite: '',
       nextWater: '',
       vegetation: '',
       expo: '',
       actualUse: '',
       other: '',
       totalArea: '',
       frontFacadeHeight: '',
       rearHacadeHeight: '',
       width: '',
       height: '',
       depth: '',
       rigdeHeight: '',
       totalHeight: '',
       headroomGround: '',
       headroomType: '',
       length: '',
       localization: '',
       content: '',
       access: '',
       notes: '',
    },
  );

  return (
    <ScrollView contentContainerStyle={styles.generalPurposeForm}>
      <Title titleText='Sibipiruna' />
      <ItemCard
        type={itemCardTypes.LARGE_TEXT}
        title={translate('DESCRIPTION_CHARACTERISTICS')}
        value={state.characteristics}
      />
      <ItemCard
        type={itemCardTypes.LARGE_TEXT}
        title={translate('DESCRIPTION_DIMENSIONS')}
        value={state.dimensions}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_TOPO_CONDITIONS')}
        value={state.topoConditions}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_USE')}
        value={state.use}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_NUMBER_ENV')}
        value={state.numberEnv}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_NUMBER_FLOOR')}
        value={state.numberFloor}
      />
      <ItemCard
        type={itemCardTypes.SWITCHER}
        title={translate('DESCRIPTION_ALCOVE')}
        value={state.alcove}
      />
      <ItemCard
        type={itemCardTypes.SWITCHER}
        title={translate('DESCRIPTION_BASEMENT')}
        value={state.basement}
      />
      <ItemCard
        type={itemCardTypes.SWITCHER}
        title={translate('DESCRIPTION_ATTIC')}
        value={state.attic}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_ANTHROPIC')}
        value={state.anthropic}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_ENV_CHARACTERISTICS')}
        value={state.envCharacteristics}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_LANDSCAPE_SITE')}
        value={state.landscapeSite}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_NEXT_WATER')}
        value={state.nextWater}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_VEGETATION')}
        value={state.vegetation}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_EXPO')}
        value={state.expo}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_ACTUAL_USE')}
        value={state.actualUse}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_OTHER')}
        value={state.other}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_TOTAL_AREA')}
        value={state.totalArea}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_FRONT_FACADE_HEIGHT')}
        value={state.frontFacadeHeight}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_REAR_FACADE_HEIGHT')}
        value={state.rearHacadeHeight}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_WIDTH')}
        value={state.width}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_HEIGHT')}
        value={state.height}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_DEPTH')}
        value={state.depth}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_RIGDE_HEIGHT')}
        value={state.rigdeHeight}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_TOTAL_HEIGHT')}
        value={state.totalHeight}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_HEADROOM_GROUND')}
        value={state.headroomGround}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_HEADROOM_TYPE')}
        value={state.headroomType}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_LENGTH')}
        value={state.length}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_LOCALIZATION')}
        value={state.localization}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_CONTENT')}
        value={state.content}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_ACCESS')}
        value={state.access}
      />
      <ItemCard
        type={itemCardTypes.TEXT}
        title={translate('DESCRIPTION_NOTES')}
        value={state.notes}
      />
    </ScrollView>
  );
};

DescriptionScreen.navigationOptions = () => ({
  headerTitle: labels.DESCRIPTION,
});

export default DescriptionScreen;